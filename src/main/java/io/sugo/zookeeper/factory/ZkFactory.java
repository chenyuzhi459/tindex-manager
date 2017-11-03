package io.sugo.zookeeper.factory;

import io.sugo.cache.Cache;
import io.sugo.http.Configure;
import io.sugo.zookeeper.ClientHandler;
import io.sugo.zookeeper.client.CuratorZookeeperClient;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ZkFactory {
    private static ZkFactory zkFactory = new ZkFactory();
    public static Configure configure;

    private ZkFactory() {}

    public static ZkFactory getFactory(Configure conf) {
        configure = conf;
        return zkFactory;
    }

    public CuratorZookeeperClient newClient() {
        return new CuratorZookeeperClient(configure);
    }


    public ClientHandler getClientHandler() throws ExecutionException {
        String[] zkServers = configure.getProperty("zk.properties","zk.servers").split(",");
        Arrays.sort(zkServers);
        return Cache.getZkClientCache(configure).get(Arrays.toString(zkServers));
    }
}
