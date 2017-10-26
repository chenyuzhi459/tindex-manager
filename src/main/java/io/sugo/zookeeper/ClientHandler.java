package io.sugo.zookeeper;

import io.sugo.zookeeper.client.CuratorZookeeperClient;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler {
    private static final Logger LOG = Logger.getLogger(ClientHandler.class);
    private final ReentrantLock lock = new ReentrantLock();
    private String clientId;
    private CuratorZookeeperClient client;

    public ClientHandler(String clientId, CuratorZookeeperClient client) {
        this.clientId = clientId;
        this.client = client;
    }

    public CuratorZookeeperClient getClient() {
        lock.lock();
        LOG.info("clientId:"+clientId);
        return client;
    }

    public void close() {

    }
}
