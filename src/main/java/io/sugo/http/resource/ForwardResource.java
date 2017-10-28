package io.sugo.http.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import io.sugo.http.util.HttpMethodProxy;

public class ForwardResource extends Resource{

    protected static Client client;
    protected static HttpMethodProxy httpMethod;
    protected static String ip;
    protected static String pathPre;
    private static final int TIME_OUT = 5*1000;

    public ForwardResource() {
        init();
    }

    private void init() {
        client = Client.create();
        client.setConnectTimeout(TIME_OUT);
        client.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, TIME_OUT);
        httpMethod = new HttpMethodProxy(client);
    }
}
