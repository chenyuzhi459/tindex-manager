package io.sugo.zookeeper.client;


import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sugo.http.Configure;
import io.sugo.zookeeper.ZkStateListener;
import io.sugo.zookeeper.ZookeeperItem;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event;

import org.apache.zookeeper.data.Stat;



/**
 * Created by chenyuzhi on 17-10-23.
 */
public class CuratorZookeeperClient {
	protected static final Logger LOG = Logger.getLogger(CuratorZookeeperClient.class);

	public static Configure configure = Configure.getConfigure();

	public static final String NONE_DATA = "None";
	private static String zkServers =configure.getProperty("zk.properties","zk.servers","192.168.0.225:2181,192.168.0.224:2181,192.168.0.223:2181");
	private final int CONNECT_TIMEOUT = configure.getInt("zk.properties","connect.timeout",15000);
	private final int RETRY_TIME = configure.getInt("zk.properties","retry.time",Integer.MAX_VALUE);
	private final int RETRY_INTERVAL = configure.getInt("zk.properties","retry.interval",1000);
	private CuratorFramework curator;

	private volatile static CuratorZookeeperClient instance;

	/**
	 * key:父路径，如/jobcenter/client/goodscenter
	 * value：Map-->key:子路径，如/jobcenter/client/goodscenter/goodscenter00000001
	 *              value:路径中的值
	 */
	private static ConcurrentHashMap<String,Map<String,String>> zkCacheMap = new ConcurrentHashMap<String,Map<String,String>>();

	public static Map<String,Map<String,String>> getZkCacheMap() {
		return zkCacheMap;
	}

	private CuratorFramework newCurator(String zkServers) {
		return CuratorFrameworkFactory.builder().connectString(zkServers)
				.retryPolicy(new RetryNTimes(RETRY_TIME, RETRY_INTERVAL))
				.connectionTimeoutMs(CONNECT_TIMEOUT).build();
	}

	private CuratorZookeeperClient(String zkServers) {
		if(curator == null) {
			curator = newCurator(zkServers);
			curator.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				public void stateChanged(CuratorFramework client, ConnectionState state) {
					if (state == ConnectionState.LOST) {
						//连接丢失
						LOG.info("lost session with zookeeper");
					} else if (state == ConnectionState.CONNECTED) {
						//连接新建
						LOG.info("connected with zookeeper");
					} else if (state == ConnectionState.RECONNECTED) {
						LOG.info("reconnected with zookeeper");
						//连接重连
						for(ZkStateListener s:stateListeners){
							s.reconnected();
						}
					}
				}
			});
			curator.start();
		}
	}

	public static CuratorZookeeperClient getInstance() {

		return getInstance(zkServers);
	}

	public static CuratorZookeeperClient getInstance(String zkServers) {
		if(instance == null) {
			synchronized(CuratorZookeeperClient.class) {
				if(instance == null) {
					LOG.info("initial CuratorZookeeperClient instance");
					instance = new CuratorZookeeperClient(zkServers);
				}
			}
		}
		return instance;
	}

	/**
	 * 写数据：/docker/jobcenter/client/app/app0..../app1...../app2
	 * @param path
	 * @param content
	 *
	 * @return 返回真正写到的路径
	 * @throws Exception
	 */
	public String create(String path, CreateMode mode, String content) {

		String writePath = null;
		try {
			StringBuilder sb = new StringBuilder(path);
			writePath = curator.create().creatingParentsIfNeeded()
					.withMode(mode)
					.forPath(sb.toString(), content.getBytes("utf-8"));
		} catch (Exception e) {
			LOG.error(MessageFormat.format("create node for path [{0}] failed",path),e);
		}
		return writePath;
	}

	public String create(String path, CreateMode mode) {
		return create(path,mode,"");
	}


	/**
	 * 随机读取一个path子路径
	 * 先从cache中读取，如果没有，再从zookeeper中查询
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String readRandom(String path) throws Exception {
		String parentPath = path;
		Map<String,String> cacheMap = zkCacheMap.get(path);
		if(cacheMap != null && cacheMap.size() > 0) {
			LOG.debug("get random value from cache,path="+path);
			return getRandomValue4Map(cacheMap);
		}
		if(curator.checkExists().forPath(path) == null) {
			LOG.debug(String.format("path [%s] is not exists,return null",path));
			return null;
		} else {
			LOG.debug("read random from zookeeper,path="+path);
			cacheMap = new HashMap<String,String>();
			List<String> list = curator.getChildren().usingWatcher(new ZKWatcher(parentPath,path)).forPath(path);
			if(list == null || list.size() == 0) {
				LOG.debug(String.format("path [%s] has no children return null",path));
				return null;
			}
			Random rand = new Random();
			String child = list.get(rand.nextInt(list.size()));
			path = path + "/" + child;
			byte[] b = curator.getData().usingWatcher(new ZKWatcher(parentPath,path))
					.forPath(path);
			String value = new String(b,"utf-8");
			if(StringUtils.isNotBlank(value)) {
				cacheMap.put(path, value);
				zkCacheMap.put(parentPath, cacheMap);
			}
			return value;
		}
	}

	/**
	 * 读取path下所有子路径下的内容
	 * 先从map中读取，如果不存在，再从zookeeper中查询
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<String> readAll(String path) throws Exception {
		String parentPath = path;
		Map<String,String> cacheMap = zkCacheMap.get(path);
		List<String> list = new ArrayList<String>();
		if(cacheMap != null) {
			LOG.debug("read all from cache,path="+path);
			list.addAll(cacheMap.values());
			return list;
		}
		if(curator.checkExists().forPath(path) == null) {
			LOG.debug(String.format("path [%s] is not exists,return null",path));
			return null;
		} else {
			cacheMap = new HashMap<String,String>();
			List<String> children = curator.getChildren().usingWatcher(new ZKWatcher(parentPath,path)).forPath(path);
			if(children == null || children.size() == 0) {
				LOG.debug(String.format("path [%s] has no children,return null",path));
				return null;
			} else {
				LOG.debug("read all from zookeeper,path="+path);
				String basePath = path;
				for(String child : children) {
					path = basePath + "/" + child;
					byte[] b = curator.getData().usingWatcher(new ZKWatcher(parentPath,path))
							.forPath(path);
					String value = new String(b,"utf-8");
					if(StringUtils.isNotBlank(value)) {
						list.add(value);
						cacheMap.put(path, value);
					}
				}
			}
			zkCacheMap.put(parentPath, cacheMap);
			return list;
		}
	}

	/**
	 * 随机获取Map中的一个值
	 * @param map
	 * @return
	 */
	private String getRandomValue4Map(Map<String,String> map) {
		Object[] values = map.values().toArray();
		Random rand = new Random();
		return values[rand.nextInt(values.length)].toString();
	}

	public String getData(String path){
		try {
			if(curator.checkExists().forPath(path) == null) {
				LOG.error(MessageFormat.format("path [{0}] is not exists,return null",path));
				return null;
			} else {
				byte[] data = curator.getData().forPath(path);
				return data == null ? "" : new String(data,"utf-8");
			}
		} catch (Exception e) {
			LOG.error(MessageFormat.format("get data for path [{0}] failed",path),e);
		}
		return null;
	}

	public Map getSummaryInfo(String path){
		try {
			if(curator.checkExists().forPath(path) == null) {
				LOG.error(MessageFormat.format("path [{0}] is not exists,return null",path));
				return null;
			} else {
				Stat zkStat = new Stat();
				byte[] data = curator.getData().storingStatIn(zkStat).forPath(path);

				return ImmutableMap.of("sourceData",data == null ? "" : new String(data,"utf-8"),
						"nodeType",zkStat.getEphemeralOwner() > 0 ? "ephemeral" : "persist");
			}
		} catch (Exception e) {
			LOG.error(MessageFormat.format("get data for path [{0}] failed",path),e);
		}
		return null;
	}

	public boolean setData(String path,String data){
		try {
			if(curator.checkExists().forPath(path) == null) {
				LOG.error(MessageFormat.format("path [{0}] is not exists",path));
				return false;
			} else {
				curator.setData().forPath(path,data.getBytes("utf-8"));
				return true;
			}
		} catch (Exception e) {
			LOG.error(MessageFormat.format("set data for path [{0}] failed",path),e);
		}
		return false;
	}

	public boolean delete(String path) {
		try {
			if(curator.checkExists().forPath(path) != null) {
				curator.delete().forPath(path);
				zkCacheMap.remove(path);
				return true;
			}
		} catch (Exception e) {
			LOG.error(MessageFormat.format("delete node for path [{0}] failed",path),e);
		}

		return false;
	}

	/**
	 * 获取路径下的所有子路径
	 * @param path
	 * @return
	 */
	public List<String> getChildren(String path) {
		try {
			if(curator.checkExists().forPath(path) == null) {
				LOG.debug(String.format("path [%s] is not exists,return null",path));
				return null;
			} else {
				List<String> children = curator.getChildren().forPath(path);
				return children;
			}
		} catch (Exception e) {
			LOG.error(MessageFormat.format("get children for path [{0}] failed",path),e);
		}
		return null;
	}



	public void close() {
		if(curator != null) {
			curator.close();
			curator = null;
		}
		zkCacheMap.clear();
	}

	/**
	 * zookeeper监听节点数据变化
	 * @author lizhiyang
	 *
	 */
	private class ZKWatcher implements CuratorWatcher {
		private String parentPath;
		private String path;
		public ZKWatcher(String parentPath,String path) {
			this.parentPath = parentPath;
			this.path = path;
		}

		public void process(WatchedEvent event) throws Exception {
			Map<String,String> cacheMap = zkCacheMap.get(parentPath);
			if(cacheMap == null) {
				cacheMap = new HashMap<String,String>();
			}
			if(event.getType() == Event.EventType.NodeDataChanged
					|| event.getType() == Event.EventType.NodeCreated){
				byte[] data = curator.getData().
						usingWatcher(this).forPath(path);
				cacheMap.put(path, new String(data,"utf-8"));
				LOG.info(String.format("add cache=%s",new String(data,"utf-8")));
			} else if(event.getType() == Event.EventType.NodeDeleted) {
				cacheMap.remove(path);
				LOG.info(String.format("remove cache path=%s",path));
			} else if(event.getType() == Event.EventType.NodeChildrenChanged) {
				//子节点发生变化，重新进行缓存
				cacheMap.clear();
				List<String> children = curator.getChildren().usingWatcher(new ZKWatcher(parentPath,path)).forPath(path);
				if(children != null && children.size() > 0) {
					for(String child : children) {
						String childPath = parentPath + "/" + child;
						byte[] b = curator.getData().usingWatcher(new ZKWatcher(parentPath,childPath))
								.forPath(childPath);
						String value = new String(b,"utf-8");
						if(StringUtils.isNotBlank(value)) {
							cacheMap.put(childPath, value);
						}
					}
				}
				LOG.info(String.format("node children changed,recaching path=%s",path));
			}
			zkCacheMap.put(parentPath, cacheMap);
		}
	}
	private final Set<ZkStateListener> stateListeners = new CopyOnWriteArraySet<ZkStateListener>();
	public void addStateListener(ZkStateListener listener) {
		stateListeners.add(listener);
	}


//	/**
//	 * zookeeper监听节点数据变化
//	 *
//	 */
//	private class ZKDataWatcher implements CuratorWatcher {
//		private String path;
//
//		public ZKDataWatcher(String path) {
//			this.path = path;
//		}
//
//		public void process(WatchedEvent event) throws Exception {
//			ZookeeperItem cacheZkItem = zkCacheMap.get(path);
//			if (cacheZkItem == null) {
//				cacheZkItem = new ZookeeperItem(path);
//			}
//			if (event.getType() == Event.EventType.NodeDataChanged
//					|| event.getType() == Event.EventType.NodeCreated) {
//				byte[] data = curator.getData().
//						usingWatcher(this).forPath(path);
//				logger.info("dataChanged,path[{}]",path);
//				cacheZkItem.setData(data != null ? new String(data,"utf-8") : ZookeeperItem.NONE_DATA);
//				zkCacheMap.put(path, cacheZkItem);
//				logger.info("add cache={}", new String(data, "utf-8"));
//			} else if (event.getType() == Event.EventType.NodeDeleted) {
//				zkCacheMap.remove(path);
//				logger.info("remove cache path={}", path);
//			}
//
//		}
//	}
}