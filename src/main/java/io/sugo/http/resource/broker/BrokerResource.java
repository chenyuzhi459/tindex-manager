package io.sugo.http.resource.broker;

import io.sugo.http.resource.ForwardResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/broker/v1")
public class BrokerResource extends ForwardResource {


    public BrokerResource() throws IOException {
        ip = configure.getProperty("druid.properties","broker.ip");
        pathPre = "http://" + ip + "/druid/broker/v1";
    }

    @GET
    @Path("/loadstatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoadStatus()
    {
        String url = String.format("%s/loadstatus",pathPre);
        return httpMethod.get(url);
    }
}

