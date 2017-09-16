package io.sugo.http.resource.Cache;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataCache {

    private static final Map<String,String> mapCache = new ConcurrentHashMap<>();
    private static final DataCache Cache = new DataCache();

    private DataCache(){

    }

    public static DataCache getDataCacheInstance(){
        return Cache;
    }

    public  String getData(String key){
        String value = mapCache.get(key);
        if(null == value){
            return null;
        }
        return value;

    }

    public String postData(String key,String value){
        mapCache.put(key,value);
        return key + " = " + value;

    }

    public String putData(String key, String value){

        if(!mapCache.keySet().contains(key)){
            return null;
        }
        String newValue = mapCache.get(key) + value;
        mapCache.put(key,newValue);
        return key + " = " + newValue;

    }

    public String deleteData(String key){

        if(!mapCache.keySet().contains(key)){
            return null;
        }
        mapCache.remove(key);
        return key;

    }

    public String getAllData(){
        return mapCache.toString();
    }





}
