package io.sugo.zookeeper.factory;

import io.sugo.http.Configure;
import io.sugo.zookeeper.client.CuratorZookeeperClient;

public class ZkFactory {
    private static ZkFactory zkFactory = new ZkFactory();
    public static Configure configure;

    private ZkFactory() {}

    public static ZkFactory getZkFactory(Configure conf) {
        configure = conf;
        return zkFactory;
    }

//    public CuratorZookeeperClient newClient() {
//        return new CuratorZookeeperClient();
//    }


//    public CuratorZookeeperClient getClient() {
//
//    }
}
