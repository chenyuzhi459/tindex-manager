package io.sugo.http.resource.systemResource;

import io.sugo.http.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/system/ip")
public class IpResource extends Resource {

    @GET
    @Path("/historical")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistoricalIps() {
        String[] historicalIps = configure.getProperty("config.properties","historicals_ip").split(",");
        if(historicalIps == null || historicalIps.length < 1) {
            return Response.status(405).build();
        }
        return Response.ok(historicalIps).build();
    }


}
