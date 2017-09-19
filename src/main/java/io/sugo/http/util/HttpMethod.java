package io.sugo.http.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.ResourcesManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class HttpMethod {

    private Client client;

    public HttpMethod(Client client) {
        this.client = client;
    }

    public ClientResponse get(WebResource resource) {
        ClientResponse cr = resource.get(ClientResponse.class);
        return cr;
    }

    public ClientResponse get(WebResource resource, MultivaluedMapImpl queryParams) {
        ClientResponse cr = resource.queryParams(queryParams).get(ClientResponse.class);
        return cr;
    }

    public ClientResponse post(WebResource resource, String data ,MultivaluedMapImpl queryParams) {
        ClientResponse cr = resource.queryParams(queryParams).post(ClientResponse.class,data);
        return cr;
    }

    public ClientResponse post(WebResource resource, MultivaluedMapImpl queryParams) {
        ClientResponse cr = resource.queryParams(queryParams).post(ClientResponse.class);
        return cr;
    }

    public ClientResponse post(WebResource resource, String data) {
        ClientResponse cr = resource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class,data);
        return cr;
    }

    public ClientResponse post(WebResource resource) {
        ClientResponse cr = resource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class);
        return cr;
    }

    public ClientResponse delete(WebResource resource) {
        ClientResponse cr = resource.delete(ClientResponse.class);
        return cr;
    }

    public ClientResponse delete(WebResource resource, MultivaluedMapImpl queryParams) {
        ClientResponse cr = resource.queryParams(queryParams).delete(ClientResponse.class);
        return cr;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
