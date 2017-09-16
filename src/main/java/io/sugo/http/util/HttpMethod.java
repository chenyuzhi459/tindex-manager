package io.sugo.http.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.ResourcesManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;

public class HttpMethod {

    protected static final Logger LOG = Logger.getLogger(ResourcesManager.class);
    private Client client;
    private WebResource resource;

    public HttpMethod(Client client) {
        this.client = client;
    }

    public String get(String url) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.get(String.class);
        return result;
    }

    public String get(String url, MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.queryParams(queryParams).get(String.class);
        return result;
    }

    public String post(String url,String data ,MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.queryParams(queryParams).post(String.class,data);
        return result;
    }

    public String post(String url, MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.queryParams(queryParams).post(String.class);
        return result;
    }

    public String post(String url,String data) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.post(String.class,data);
        return result;
    }

    public String post(String url) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.post(String.class);
        return result;
    }

    public String delete(String url) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.delete(String.class);
        return result;
    }

    public String delete(String url, MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = client.resource(url);
        String result = resource.queryParams(queryParams).delete(String.class);
        return result;
    }
}
