package io.sugo.cache;

import com.google.common.cache.*;
import io.sugo.http.Configure;
import io.sugo.kafka.ConsumerHandler;
import io.sugo.kafka.factory.KafkaFactory;
import io.sugo.zookeeper.ClientHandler;
import io.sugo.zookeeper.factory.ZkFactory;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by chenyuzhi on 17-10-23.
 */
public class CacheNew {
	private static final Logger LOG = Logger.getLogger(CacheNew.class);
	private static LoadingCache<String,ConsumerHandler> kafkaConsumerCache;
	private static LoadingCache<String,ClientHandler> zkClientCache;
	private static Object lock = new Object();


	public static LoadingCache<String,ConsumerHandler> getKafkaConsumerCache(final Configure configure) {
		if(null == kafkaConsumerCache){        //lazy initialization
			synchronized (lock){
				if(null == kafkaConsumerCache){
					LOG.info("creating kafkaConsumerCache...");
					kafkaConsumerCache = CacheBuilder.newBuilder()
							.maximumSize(100)
							.expireAfterAccess(10, TimeUnit.MINUTES)
							.removalListener(new RemovalListener<String, ConsumerHandler>() {
								public void onRemoval(RemovalNotification<String, ConsumerHandler> removal){
									removal.getValue().close();
								}
							})
							.build(
									new CacheLoader<String, ConsumerHandler>() {
										@Override
										public ConsumerHandler load(String s) throws Exception {
											LOG.info("created ConsumerHandler with key:"+s);
											return new ConsumerHandler(s,KafkaFactory.getFactory(configure).newConsumer());
										}
									}
							);
					LOG.info("kafkaConsumerCache has created successfully...");
				}
			}
		}
		return kafkaConsumerCache;
	}

	public static LoadingCache<String,ClientHandler> getZkClientCache(final Configure configure) {
		if(null == zkClientCache){        //lazy initialization
			synchronized (lock){
				if(null == zkClientCache){
					LOG.info("creating zkClientCache...");
					zkClientCache = CacheBuilder.newBuilder()
							.maximumSize(100)
							.expireAfterAccess(10, TimeUnit.MINUTES)
							.removalListener(new RemovalListener<String, ClientHandler>() {
								public void onRemoval(RemovalNotification<String, ClientHandler> removal){
									removal.getValue().close();
								}
							})
							.build(
									new CacheLoader<String, ClientHandler>() {
										@Override
										public ClientHandler load(String s) throws Exception {
											LOG.info("created ConsumerHandler with key:"+s);
											return new ClientHandler(s, ZkFactory.getFactory(configure).newClient());
										}
									}
							);
					LOG.info("kafkaConsumerCache has created successfully...");
				}
			}
		}
		return zkClientCache;
	}
}
