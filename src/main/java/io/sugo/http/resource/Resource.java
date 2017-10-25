package io.sugo.http.resource;

import com.sun.jersey.api.client.Client;
import io.sugo.http.Configure;
import io.sugo.http.util.HttpMethodProxy;

public class Resource {

    protected static Configure configure = Configure.getConfigure();
    protected static Client client;
    protected static HttpMethodProxy httpMethod;
    protected static String ip;
    protected static String pathPre;

    public Resource() {
        init();
    }

    private void init() {
//        configure = new Configure();
        client = Client.create();
        httpMethod = new HttpMethodProxy(client);
    }
}
