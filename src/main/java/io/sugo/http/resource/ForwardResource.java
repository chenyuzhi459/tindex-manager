package io.sugo.http.resource;

import com.sun.jersey.api.client.Client;
import io.sugo.http.util.HttpMethodProxy;

public class ForwardResource extends Resource{

    protected static Client client;
    protected static HttpMethodProxy httpMethod;
    protected static String ip;
    protected static String pathPre;

    public ForwardResource() {
        init();
    }

    private void init() {
        client = Client.create();
        httpMethod = new HttpMethodProxy(client);
    }
}
