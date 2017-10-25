package io.sugo.kafka.factory;

import io.sugo.cache.Cache;
import io.sugo.http.Configure;
import io.sugo.kafka.ConsumerHandler;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenyuzhi on 17-10-23.
 */
public class KafkaFactory {

	private static  KafkaFactory factory = new KafkaFactory();
	public static Configure configure;
	private KafkaFactory(){
	}

	public static KafkaFactory getFactory(Configure conf){
		configure = conf;
		return factory;
	}

	public  KafkaConsumer<byte[], byte[]> newConsumer() {
		ClassLoader currCtxCl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

			final Properties props = new Properties();
			//            kafka.bootstrap.servers=192.168.0.220:9092,192.168.0.221:9092,192.168.0.222:9092
			props.setProperty("bootstrap.servers", configure.getProperty("kafka.properties","bootstrap.servers","192.168.0.223:9092,192.168.0.224:9092,192.168.0.225:9092"));
			//      props.setProperty("bootstrap.servers", "192.168.0.220:9092,192.168.0.221:9092,192.168.0.222:9092");
			props.setProperty("enable.auto.commit", configure.getProperty("kafka.properties","enable.auto.commit","false"));
			props.setProperty("auto.offset.reset", configure.getProperty("kafka.properties","auto.offset.reset","none"));
			props.setProperty("key.deserializer", configure.getProperty("kafka.properties","key.deserializer",ByteArrayDeserializer.class.getName()));
			props.setProperty("value.deserializer", configure.getProperty("kafka.properties","value.deserializer",ByteArrayDeserializer.class.getName()));
			System.out.println("kafka consumer properties:" + props);
			return new KafkaConsumer<>(props);
		} finally {
			Thread.currentThread().setContextClassLoader(currCtxCl);
		}
	}

	public ConsumerHandler getConsumer() throws ExecutionException {
		return Cache.getKafkaConsumerCache(configure).get("consumer");
	}

	public ConsumerHandler getConsumer(String key) throws ExecutionException {
		return Cache.getKafkaConsumerCache(configure).get(key);
	}

}
