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
  private static final String CONFIG_PATH = "src/main/resources/config/";
  private static final String CONFIG_PROPERTIES = "config.properties";
  private static final String SYSTEM_PROPERTIES = "system.properties";
  private static final String KAFKA_PROPERTIES = "kafka.properties";

  private String configConf;
  private String systemConf;
  private String kafkaConf;
  private Properties configProperties = new Properties();
  private Properties systemProperties = new Properties();
  private Properties kafkaProperties = new Properties();
  private Map<String, Properties> allProperties = new HashMap<>();


  public Configure() {
    loadConf();
    addAllProperties();
  }

  private void loadConf() {
    loadConf(CONFIG_PROPERTIES,configProperties);
    loadConf(SYSTEM_PROPERTIES,systemProperties);
    loadConf(KAFKA_PROPERTIES,kafkaProperties);
  }

  private void addAllProperties() {
    allProperties.put("config.properties",configProperties);
    allProperties.put("system.properties",systemProperties);
  }

  private void loadConf(String confName,Properties properties) {
    try {
      LOG.info("---------------------------------------");
      String filePath = CONFIG_PATH + confName;
      properties.load(new FileInputStream(filePath));
      LOG.info("confName: " + confName);
      for (Object key : properties.keySet()) {
        LOG.info(key + " : " + properties.getProperty(key.toString()));
      }
    } catch (IOException ix) {
      ix.printStackTrace();
    }
  }

  public String getProperty(String propName,String key) {
    return getProperty(propName, key, "");
  }

  public String getProperty(String propName, String key, String defaultValue) {
    if(allProperties.containsKey(propName)) {
      return allProperties.get(propName).getProperty(key, defaultValue);
    } else {
      return "";
    }
  }

  public Properties getProperties(String propName) {
    if(allProperties.containsKey(propName)) {
      return allProperties.get(propName);
    } else {
      return null;
    }
  }

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

    for(Map.Entry<String,Properties> entry : allProperties.entrySet()) {
      propertiesMap = getAllPropertiesFromAPropertyFile(propertiesMap, allProperties.get(entry.getKey()));
    }
    return propertiesMap;
  }

  public Map<String, String> getAllPropertiesFromAPropertyFile(Map<String, String> propertiesMap, Properties properties) {
    Set<String> propertyNamesSet = properties.stringPropertyNames();
    Iterator<String> it = propertyNamesSet.iterator();
    while (it.hasNext()) {
      String propertiesName = it.next();
      propertiesMap.put(propertiesName, properties.getProperty(propertiesName));
    }
    return propertiesMap;
  }

}
