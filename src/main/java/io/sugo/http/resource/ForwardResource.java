package io.sugo.http.resource;


import io.sugo.http.ResourcesManager;
import io.sugo.http.jersy.JersyClientFactory;
import io.sugo.http.util.HttpMethodProxy;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import sun.misc.Cleaner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;

public class ForwardResource extends Resource{

    private static final Logger LOG = Logger.getLogger(ForwardResource.class);
    protected static Client client;
    protected static HttpMethodProxy httpMethod;
    protected static String ip;
    protected static String pathPre;
    private static final int TIME_OUT = 5*1000;

    public ForwardResource() {
        init();
    }

    private void init() {
//        ClientConfig clientConfig = new ClientConfig();
//
//        clientConfig.register(JacksonFeature.class);
//
//
//        client = ClientBuilder.newClient(clientConfig);

            client = JersyClientFactory.create();
        httpMethod = new HttpMethodProxy(client);
    }
}
