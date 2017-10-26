package io.sugo.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chenyuzhi on 17-10-23.
 */
public class ConsumerHandler {
	private static final Logger LOG = Logger.getLogger(ConsumerHandler.class);

	private final ReentrantLock lock = new ReentrantLock();
	private final String conusmerId;
	private final KafkaConsumer consumer;

	public ConsumerHandler(String conusmerId, KafkaConsumer consumer) {
		this.conusmerId = conusmerId;
		this.consumer = consumer;
	}

	public KafkaConsumer getConsumer(){
		lock.lock();
		LOG.info("conusmerId:"+conusmerId);
		return consumer;
	}



	public void close(){
		consumer.close();
	}

	public void clear(){
		lock.unlock();
	}
}
