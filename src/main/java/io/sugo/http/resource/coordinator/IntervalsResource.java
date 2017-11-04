package io.sugo.http.resource.coordinator;

import com.google.common.collect.Maps;

import io.sugo.http.resource.ForwardResource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


//@Path("/druid/coordinator/v1/intervals")
public class IntervalsResource extends ForwardResource {


    public IntervalsResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator.ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/intervals";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIntervals(
            @Context final HttpServletRequest req,
            @QueryParam("isAscending") @DefaultValue("false") boolean isAscending
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        queryParams.put("isAscending",isAscending);
        String url = String.format("%s", pathPre);
        return httpMethod.get(url, queryParams);
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
        Map<String,Object> queryParams = Maps.newHashMap();
        if(simple != null){
            queryParams.put("simple",simple);
        }
        if(full != null){
            queryParams.put("full",full);
        }
        String url = String.format("%s/%s", pathPre, interval);
        return httpMethod.get(url,queryParams);
    }
}

