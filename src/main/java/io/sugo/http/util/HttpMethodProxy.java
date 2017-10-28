package io.sugo.http.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class HttpMethodProxy {

    private HttpMethod httpMethod;
    protected static final Logger LOG = Logger.getLogger(HttpMethodProxy.class);
    private WebResource resource;

    public HttpMethodProxy(Client client) {
        this.httpMethod = new HttpMethod(client);
    }

    public Response get(String url) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.get(resource);
        return convertClientResponse2Response(cr);
    }

    public Response getWithHeader(String url,Map<String,Object> header) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.getWithHeader(resource,header);
        return convertClientResponse2Response(cr);
    }

    public Response get(String url, MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.get(resource,queryParams);
        return convertClientResponse2Response(cr);
    }

    public Response post(String url, String data ,MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.post(resource,data,queryParams);
        return convertClientResponse2Response(cr);
    }

    public Response post(String url, MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.post(resource,queryParams);
        return convertClientResponse2Response(cr);
    }

    public Response post(String url, String data) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.post(resource,data);
        return convertClientResponse2Response(cr);
    }

    public Response post(String url) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.post(resource);
        return convertClientResponse2Response(cr);
    }

    public Response delete(String url) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.delete(resource);
        return convertClientResponse2Response(cr);
    }

    public Response delete(String url, MultivaluedMapImpl queryParams) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.delete(resource,queryParams);
        return convertClientResponse2Response(cr);
    }

    public Response delete(String url, String data) {
        LOG.info(url);
        resource = httpMethod.getClient().resource(url);
        ClientResponse cr = httpMethod.delete(resource,data);
        return convertClientResponse2Response(cr);
    }


    public static Response convertClientResponse2Response(ClientResponse r) {
        Response.ResponseBuilder rb = Response.status(r.getStatus());
        for (Map.Entry<String, List<String>> entry : r.getHeaders().entrySet()) {
            for (String value : entry.getValue()) {
                rb.header(entry.getKey(), value);
            }
        }
        rb.entity(r.getEntityInputStream());
        return rb.build();
    }
}
