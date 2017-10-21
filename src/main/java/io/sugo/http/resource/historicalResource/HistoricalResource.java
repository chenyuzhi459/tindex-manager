package io.sugo.http.resource.historicalResource;

import com.google.common.base.Strings;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/historical/v1")
public class HistoricalResource extends Resource {

    public HistoricalResource() throws IOException {
        pathPre =  "/druid/historical/v1";
    }
    @GET
    @Path("/loadstatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoadStatus(
            @QueryParam("ip") String ip
    )
    {
        if(Strings.isNullOrEmpty(ip)) {
            return Response.status(400).build();
        }
        String url = String.format("http://%s%s", ip, pathPre);
        return httpMethod.get(url);
    }
}

