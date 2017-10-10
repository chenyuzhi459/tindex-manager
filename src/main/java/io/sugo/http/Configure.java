package io.sugo.http;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by fengxj on 3/29/17.
 */
public class Configure {
  private static final Logger LOG = Logger.getLogger(Configure.class);
  private static final String CLASSPATH_URL_PREFIX = "classpath:";
  private static final String CONFIG_PATH = "config/";
  private static final String CONFIG_PROPERTIES = "config.properties";
  private static final String SYSTEM_PROPERTIES = "system.properties";

  public static final String WRITER_CLASS = "writer.class";
  public static final String READER_CLASS = "reader.class";
  public static final String PARSER_CLASS = "parser.class";
  public static final String FILE_READER_BATCH_SIZE = "file.reader.batch.size";
  public static final String READER_MESSAGE_MAX_SIZE_BYTES = "reader.message.max_size.bytes";
  public static final int DEFAULT_READER_MESSAGE_MAX_SIZE_BYTES = 1048576;
  public static final String ERROR_MESSAGE_HTTP_COLLECTOR_URL = "error.message.http_collector.url";
  public static final String  METRIC_SERVER_URL = "metric.server.url";
  public static final String METRIC_SEND_INTERVAL = "metric.send.interval.ms";

  private String configConf;
  private String systemConf;
  private Properties configProperties = new Properties();
  private Properties systemProperties = new Properties();

  public Configure() {
    configConf = System.getProperty(CONFIG_PROPERTIES, CLASSPATH_URL_PREFIX + CONFIG_PATH + CONFIG_PROPERTIES);
    systemConf = System.getProperty(SYSTEM_PROPERTIES, CLASSPATH_URL_PREFIX + CONFIG_PATH + SYSTEM_PROPERTIES);
    loadConf();
  }

  private void loadConf() {

    loadConf(configConf,configProperties);
    loadConf(systemConf,systemProperties);
  }

  private void loadConf(String conf,Properties properties) {
    try {
      if (conf.startsWith(CLASSPATH_URL_PREFIX)) {
          conf = StringUtils.substringAfter(conf, CLASSPATH_URL_PREFIX);
//          properties.load(Configure.class.getClassLoader().getResourceAsStream(conf));
        String filePath = "src/main/resources/" + conf;
        properties.load(new FileInputStream(filePath));
      } else {
          properties.load(new FileInputStream(conf));
      }
      for (Object key : properties.keySet()) {
        LOG.info(key + " : " + properties.getProperty(key.toString()));
      }
    } catch (IOException ix) {
      ix.printStackTrace();
    }
  }

  public String getProperty(String propName,String key) {
    if(propName.contains("config")) {
      return configProperties.getProperty(key);
    } else if(propName.contains("system")) {
      return systemProperties.getProperty(key);
    }
    return "";
  }
  public String getProperty(String propName, String key, String defaultValue) {
    Properties properties = new Properties();
    if(propName.contains("config")) {
      properties = configProperties;
    } else if(propName.contains("system")) {
      properties = systemProperties;
    }

    if (!properties.containsKey(key))
      return defaultValue;
    return getProperty(propName,key);
  }

  public Properties getProperties(String propName) {
    if(propName.contains("config")) {
      return configProperties;
    } else if(propName.contains("system")) {
      return systemProperties;
    }
    return null;
  }

//  public int getInt(String key, int defaultValue) {
//    if (!properties.containsKey(key))
//      return defaultValue;
//    return getInt(key);
//  }
//
  public int getInt(String propName,String key) {
    String value = getProperty(propName,key);
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      LOG.error("", e);
    }
    return 0;
  }

  public int getInt(String propName,String key,int defaultValue) {

    String value = getProperty(propName,key,defaultValue+"");
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      LOG.error("", e);
    }
    return 0;
  }

  public boolean getBoolean(String propName, String key) {
    String value = getProperty(propName,key);
    try {
      return Boolean.parseBoolean(value);
    } catch (Exception e) {
      LOG.error("", e);
    }
    return false;
  }

  public boolean getBoolean(String propName,String key,boolean defaultValue) {

    String value = getProperty(propName,key,defaultValue+"");
    try {
      return Boolean.parseBoolean(value);
    } catch (Exception e) {
      LOG.error("", e);
    }
    return false;
  }

  public Map<String, String> getAllProperties() {
    Map<String, String> propertiesMap = new HashMap<>();
    propertiesMap = getAllPropertiesFromOneProperty(propertiesMap, configProperties);
    return getAllPropertiesFromOneProperty(propertiesMap, systemProperties);
  }

  public Map<String, String> getAllPropertiesFromOneProperty(Map<String, String> propertiesMap, Properties properties) {
    Set<String> propertyNamesSet = properties.stringPropertyNames();
    Iterator<String> it = propertyNamesSet.iterator();
    while (it.hasNext()) {
      String propertiesName = it.next();
      propertiesMap.put(propertiesName, properties.getProperty(propertiesName));
    }
    return propertiesMap;
  }

}
