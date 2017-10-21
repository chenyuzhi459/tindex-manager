package io.sugo.kafka;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KafkaApi implements Closeable {
  //  private static final Logger log = LoggerFactory.getLogger(KafkaApi.class);

  private final KafkaConsumer<byte[], byte[]> consumer;

  public KafkaApi() {
    consumer = newConsumer();
  }

  public static void main(String[] args) {
    KafkaApi api = new KafkaApi();
//    api.printTopic();
//    api.printTopicPartition();
//
//    api.printTopicPartition("wuxianjiRT");
    api.printTopicPartitionOffset();
  }


  public void printTopicPartition(String topic) {
    List<PartitionInfo> partitions = consumer.partitionsFor(topic);
    for (PartitionInfo info : partitions) {
      TopicPartition tp = new TopicPartition(info.topic(), info.partition());
      List<TopicPartition> topicPartitions = new ArrayList<>();
      topicPartitions.add(tp);
      consumer.assign(topicPartitions);
      consumer.seekToBeginning(topicPartitions);
      long startOffset = 0;
      for (TopicPartition topicPartition : topicPartitions) {
        startOffset = consumer.position(topicPartition);
      }
      consumer.seekToEnd(topicPartitions);
      for (TopicPartition topicPartition : topicPartitions) {
        long endOffset = consumer.position(topicPartition);
        System.out.println(String.format("Partition{topic:%s, partition:%d, start offset:%,d, end offset:%,d}",
            info.topic(), topicPartition.partition(), startOffset, endOffset));
      }
    }
  }

  public void printTopic() {
    System.out.println("-------------printTopic---------------");
    Map<String, List<PartitionInfo>> topicMap = consumer.listTopics();
    for (Map.Entry<String, List<PartitionInfo>> entry : topicMap.entrySet()) {
      System.out.println(String.format("topic:%s, partitions:%d", entry.getKey(), entry.getValue().size()));
      for (PartitionInfo info : entry.getValue()) {
        System.out.println(String.format("PartitionInfo{partition:%d, inSyncReplicas:%s, leader:%s, replicas:%s}",
            info.partition(), info.inSyncReplicas(), info.leader(), info.replicas()));
      }
      System.out.println();
    }
  }

  public void printTopicPartition() {
    System.out.println("-------------printTopicPartition---------------");
    Map<String, List<PartitionInfo>> topicMap = consumer.listTopics();
    for (String topic : topicMap.keySet()) {
      System.out.println("topic:" + topic);
      List<PartitionInfo> partitions = consumer.partitionsFor(topic);
      for (PartitionInfo info : partitions) {
        System.out.println(String.format("PartitionInfo{partition:%d, inSyncReplicas:%s, leader:%s, replicas:%s}",
            info.partition(), info.inSyncReplicas(), info.leader(), info.replicas()));
      }
    }
  }

  public Map getTopicPartitionOffset(String topic, List<Integer> partitionIds) {
    List<PartitionInfo> partitions = consumer.partitionsFor(topic);
    List<Map<String,Object>> retPartitions = Lists.newArrayList();
    for (PartitionInfo info : partitions) {
      if(!partitionIds.contains(info.partition())){
        continue;
      }
      TopicPartition tp = new TopicPartition(info.topic(), info.partition());
      List<TopicPartition> topicPartitions = new ArrayList<>();
      topicPartitions.add(tp);
      consumer.assign(topicPartitions);
      consumer.seekToBeginning(topicPartitions);
      long startOffset = 0;
      for (TopicPartition topicPartition : topicPartitions) {
        startOffset = consumer.position(topicPartition);
      }
      consumer.seekToEnd(topicPartitions);
      for (TopicPartition topicPartition : topicPartitions) {
        long endOffset = consumer.position(topicPartition);
        Map<String,Object> partitionMap = Maps.newLinkedHashMap();
        partitionMap.put("partition",topicPartition.partition());
        partitionMap.put("startOffset",startOffset);
        partitionMap.put("endOffset",endOffset);
        retPartitions.add(partitionMap);
        /*System.out.println(String.format("Partition{topic:%s, partition:%d, start offset:%,d, end offset:%,d}",
                info.topic(), topicPartition.partition(), startOffset, endOffset));*/
      }

    }
    return ImmutableMap.of("topic",topic,"partitions",retPartitions);

  }

  public void printTopicPartitionOffset() {
    Map<String, List<PartitionInfo>> topicMap = consumer.listTopics();
    for (Map.Entry<String, List<PartitionInfo>> entry : topicMap.entrySet()) {
      System.out.println(String.format("topic:%s, partitions:%d", entry.getKey(), entry.getValue().size()));

      for (PartitionInfo info : entry.getValue()) {
        TopicPartition tp = new TopicPartition(entry.getKey(), info.partition());
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(tp);   //只有一个partition,为何还要用list?
        consumer.assign(partitions);
        consumer.seekToBeginning(partitions);
        long startOffset = 0;
        for (TopicPartition partition : partitions) {
          startOffset = consumer.position(partition);
        }
        consumer.seekToEnd(partitions);
        for (TopicPartition partition : partitions) {
          long endOffset = consumer.position(partition);
          System.out.println(String.format("Partition{topic:%s, partition:%d, start offset:%,d, end offset:%,d}",
                  entry.getKey(), partition.partition(), startOffset, endOffset));
        }
      }

      System.out.println();
    }
  }

  private KafkaConsumer<byte[], byte[]> newConsumer() {
    ClassLoader currCtxCl = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

      final Properties props = new Properties();
      //            kafka.bootstrap.servers=192.168.0.220:9092,192.168.0.221:9092,192.168.0.222:9092
      props.setProperty("bootstrap.servers", "192.168.0.223:9092,192.168.0.224:9092,192.168.0.225:9092");
      //      props.setProperty("bootstrap.servers", "192.168.0.220:9092,192.168.0.221:9092,192.168.0.222:9092");
      props.setProperty("enable.auto.commit", "false");
      props.setProperty("auto.offset.reset", "none");
      props.setProperty("key.deserializer", ByteArrayDeserializer.class.getName());
      props.setProperty("value.deserializer", ByteArrayDeserializer.class.getName());

      System.out.println("kafka consumer properties:" + props);
      return new KafkaConsumer<>(props);
    } finally {
      Thread.currentThread().setContextClassLoader(currCtxCl);
    }
  }

  //  private void assignPartitions() {
  //    List<TopicPartition> topicPartitions = new ArrayList<>();
  //    topicPartitions.add(new TopicPartition(topic, partition));
  //    consumer.assign(topicPartitions);
  //    logger.info("Thread:" + threadNum + " Seeking partition[" + partition + "] to offset[" + offset + "].");
  //    consumer.seek(new TopicPartition(topic, partition), offset);
  //  }

  //  public void loadToFile() throws IOException {
  //    logger.info("Thread:" + threadNum + " begin to load data from kafka to file");
  //    logger.info("Thread:" + threadNum + " topic:" + topic + ", partition:" + partition);
  //    boolean reading = true;
  //
  //    ConsumerRecords<byte[], byte[]> records = ConsumerRecords.empty();
  //    File path = new File(dataPath);
  //    if (!path.exists()) {
  //      path.mkdirs();
  //    }
  //    FileOutputStream fos = null;
  //    BufferedOutputStream bos = null;
  //    ObjectOutputStream oos = null;
  //
  //    int recordCount = 0;
  //    long start = System.currentTimeMillis();
  //    int logIndex = 1;
  //    long dataSize = 0;
  //    long refresh = new DateTime().plusMinutes(1).getMillis();
  //    try {
  //      currentFile = new File(path, "data-" + fileIndex++);
  //      fos = new FileOutputStream(currentFile);
  //      bos = new BufferedOutputStream(fos);
  //
  //      long lastRecordCount = 0;
  //      boolean waiting = true;
  //      while (reading) {
  //        records = consumer.poll(POLL_TIMEOUT);
  //        for (ConsumerRecord<byte[], byte[]> record : records) {
  //          recordCount++;
  //          offsetMap.put(record.partition(), record.offset());
  //          final byte[] valueBytes = record.value();
  //          if (valueBytes == null) {
  //            throw new RuntimeException("null value");
  //          }
  //          bos.write(valueBytes, 0, valueBytes.length);
  //          bos.write(LINE);
  //          if (currentFile.length() > MAX_FILE_LEN) {
  //            bos.close();
  //            dataSize += currentFile.length();
  //            currentFile = new File(path, "data-" + fileIndex++);
  //            fos = new FileOutputStream(currentFile);
  //            bos = new BufferedOutputStream(fos);
  //          }
  //        }
  //        if (System.currentTimeMillis() > refresh) {
  //          logger.info("Thread:" + threadNum + " " + logIndex++ + " record count:" + recordCount + ", add record:" + (recordCount - lastRecordCount) + ", partition offsets:" + offsetMap);
  //          bos.flush();
  //          refresh = new DateTime().plusMinutes(1).getMillis();
  //          if (lastRecordCount == recordCount) {
  //            refresh = new DateTime().plusSeconds(10).getMillis();
  //            if (waiting) {
  //              waiting = false;
  //            } else {
  //              logger.info("Thread:" + threadNum + " no record for 10s, stop reading");
  //              break;
  //            }
  //          }
  //          lastRecordCount = recordCount;
  //        }
  //      }
  //    } finally {
  //      if (bos != null) {
  //        bos.close();
  //      }
  //      dataSize += currentFile.length();
  //      logger.info("Thread:" + threadNum + " total record count:" + recordCount + ", files:" + fileIndex
  //          + ", data size:" + String.format("%,d", dataSize)
  //          + ", spend time:" + ((System.currentTimeMillis() - start) / 1000) + ", partition offsets:" + offsetMap);
  //      if (consumer != null) {
  //        consumer.close();
  //      }
  //      latch.countDown();
  //    }
  //  }

  @Override public void close() throws IOException {
    consumer.close();
  }

}
