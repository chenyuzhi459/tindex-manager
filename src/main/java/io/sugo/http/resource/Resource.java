package io.sugo.http.resource;

import com.sun.jersey.api.client.Client;
import io.sugo.http.Configure;
import io.sugo.http.ResourcesManager;
import io.sugo.http.util.HttpMethod;
import org.apache.log4j.Logger;

public abstract class Resource {

    protected static Configure configure = new Configure();
    protected static Client client = Client.create();
    protected static final Logger LOG = Logger.getLogger(ResourcesManager.class);
    protected static HttpMethod httpMethod = new HttpMethod(client);
    protected static String ip;
    protected static String pathPre;
}
