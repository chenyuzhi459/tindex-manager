package io.sugo.http.resource;

import com.sun.jersey.api.client.Client;
import io.sugo.http.Configure;
import io.sugo.http.util.HttpMethodProxy;
import org.apache.log4j.Logger;

public abstract class Resource {
    protected static Configure configure = Configure.getConfigure();
}
