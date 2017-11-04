package io.sugo.http.util;




import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class HttpMethod {

    private Client client;

    public HttpMethod(Client client) {
        this.client = client;
    }

    public Response get(WebTarget target) {
        Response rep = target.request().get(Response.class);
        return rep;
    }

    public Response get(WebTarget target, Map<String,Object> queryParams) {
        WebTarget withQueryParamTaget = target;
        for(Map.Entry<String,Object> param: queryParams.entrySet()){
            withQueryParamTaget = withQueryParamTaget.queryParam(param.getKey(),param.getValue());
        }
        Response rep = withQueryParamTaget.request().get(Response.class);
        return rep;
    }


    public Response getDirectly(WebTarget target, Map<String,Object> queryParams) {
        WebTarget withQueryParamTaget = target;
        for(Map.Entry<String,Object> param: queryParams.entrySet()){
            withQueryParamTaget = withQueryParamTaget.queryParam(param.getKey(),param.getValue());
        }
        Response rep = withQueryParamTaget.request().get(Response.class);
        return rep;
    }

    public Response getWithHeader(WebTarget target,Map<String,Object> header) {
        Invocation.Builder builder = target.request();
        for(String key : header.keySet()){
            builder.header(key,header.get(key));
        }
        Response rep = builder.get(Response.class);
        return rep;
    }

    public Response post(WebTarget target, String data ,Map<String,Object> queryParams) {
        WebTarget withQueryParamTaget = target;
        for(Map.Entry<String,Object> param: queryParams.entrySet()){
            withQueryParamTaget = withQueryParamTaget.queryParam(param.getKey(),param.getValue());
        }
        Response rep = withQueryParamTaget.request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(data,MediaType.APPLICATION_JSON),Response.class);
        return rep;
    }

    public Response post(WebTarget target, Map<String,Object> queryParams) {
        WebTarget withQueryParamTaget = target;
        for(Map.Entry<String,Object> param: queryParams.entrySet()){
            withQueryParamTaget = withQueryParamTaget.queryParam(param.getKey(),param.getValue());
        }
        Response rep = withQueryParamTaget.request()
                .accept(MediaType.APPLICATION_JSON)
                .build("POST").invoke(Response.class);
        return rep;
    }

    public Response post(WebTarget target, String data) {
        Response rep = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(data,MediaType.APPLICATION_JSON),Response.class);
        return rep;
    }

    public Response post(WebTarget target) {
        Response rep = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .build("POST").invoke(Response.class);
        return rep;
    }

    public Response delete(WebTarget target) {
        Response rep = target.request().delete(Response.class);
        return rep;
    }

    public Response delete(WebTarget target, Map<String,Object> queryParams) {
        WebTarget withQueryParamTaget = target;
        for(Map.Entry<String,Object> param: queryParams.entrySet()){
            withQueryParamTaget = withQueryParamTaget.queryParam(param.getKey(),param.getValue());
        }
        Response rep = withQueryParamTaget.request().delete(Response.class);
        return rep;
    }

//    public Response delete(WebTarget target, String data) {
//        Response rep = target.request()
//                .accept(MediaType.APPLICATION_JSON)
//                .type(MediaType.APPLICATION_JSON)
//                .delete(Response.class, data);
//        return rep;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
