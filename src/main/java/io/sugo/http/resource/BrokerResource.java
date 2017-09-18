package io.sugo.http.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/broker/v1")
public class BrokerResource extends Resource{


    public BrokerResource() throws IOException {
        ip = configure.getProperty("config.properties","broker_ip");
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

