package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/coordinator/v1/intervals")
public class IntervalsResource extends Resource{


    public IntervalsResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/intervals";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIntervals(@Context final HttpServletRequest req)
    {
        String url = String.format("%s");
        return httpMethod.get(url);
    }

    @GET
    @Path("/{interval}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecificIntervals(
            @PathParam("interval") String interval,
            @QueryParam("simple") String simple,
            @QueryParam("full") String full,
            @Context final HttpServletRequest req
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(simple != null){
            queryParams.add("simple",simple);
        }
        if(full != null){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s", pathPre, interval);
        return httpMethod.get(url,queryParams);
    }
}

