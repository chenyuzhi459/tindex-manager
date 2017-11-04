package io.sugo.http.resource.coordinator;

import com.google.common.collect.Maps;

import io.sugo.http.resource.ForwardResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/druid/coordinator/v1/tiers")
public class TiersResource extends ForwardResource {


    public TiersResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator.ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/tiers";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTiers(
            @QueryParam("simple") String simple
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(simple != null){
            queryParams.put("simple",simple);
        }
        String url = String.format("%s", pathPre);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{tierName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTierDatasources(
            @PathParam("tierName") String tierName,
            @QueryParam("simple") String simple
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(simple != null){
            queryParams.put("simple",simple);
        }
        String url = String.format("%s/%s", pathPre, tierName);
        return httpMethod.get(url,queryParams);
    }

}

