package io.sugo.http.resource.historical;

import com.google.common.base.Strings;
import io.sugo.http.resource.ForwardResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/historical/v1")
public class HistoricalResource extends ForwardResource {

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

