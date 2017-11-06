package io.sugo.http.util;


import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class HttpMethodProxy {

    private HttpMethod httpMethod;
    protected static final Logger LOG = Logger.getLogger(HttpMethodProxy.class);
    private WebTarget target;

    public HttpMethodProxy(Client client) {
        this.httpMethod = new HttpMethod(client);
    }

    public Response get(String url) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.get(target);
        return rep;
    }

    public Response getWithHeader(String url,Map<String,Object> header) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.getWithHeader(target,header);
        return rep;
    }

    public Response get(String url, Map<String,Object> queryParams) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.get(target,queryParams);
        return rep;
    }



    public Response post(String url, String data ,Map<String,Object> queryParams) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.post(target,data,queryParams);
        return rep;
    }

    public Response post(String url, Map<String,Object> queryParams) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.post(target,queryParams);
        return rep;
    }

    public Response post(String url, String data) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.post(target,data);
        return rep;
    }

    public Response post(String url) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.post(target);
        return rep;
    }

    public Response delete(String url) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.delete(target);
        return rep;
    }

    public Response delete(String url, Map<String,Object> queryParams) {
        LOG.info(url);
        target = httpMethod.getClient().target(url);
        Response rep = httpMethod.delete(target,queryParams);
        return rep;
    }

//    public Response delete(String url, String data) {
//        LOG.info(url);
//        target = httpMethod.getClient().target(url);
//        Response rep = httpMethod.delete(target,data);
//        return rep;
//    }


    public static Response convertResponse2Response(ClientResponse r) {
        Response.ResponseBuilder rb = Response.status(r.getStatus());
        for (Map.Entry<String, List<String>> entry : r.getHeaders().entrySet()) {
            for (String value : entry.getValue()) {
                rb.header(entry.getKey(), value);
            }
        }
//        rb.entity(r.getEntityInputStream());
        rb.entity(r.getEntity());
        return rb.build();
    }
}
