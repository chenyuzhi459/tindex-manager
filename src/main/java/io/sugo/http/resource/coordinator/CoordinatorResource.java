package io.sugo.http.resource.coordinator;

import com.google.common.collect.Maps;
import io.sugo.http.resource.ForwardResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/druid/coordinator/v1")
public class CoordinatorResource extends ForwardResource {
    public CoordinatorResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator.ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1";
    }

    @GET
    @Path("/leader")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeader() {
        String url = String.format("%s/leader", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/loadstatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoadStatus(
            @QueryParam("simple") String simple,
            @QueryParam("full") String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(simple != null){
            queryParams.put("simple",simple);
        }
        if(full != null){
            queryParams.put("full",full);
        }

        String url = String.format("%s/loadstatus", pathPre);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/loadqueue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoadQueue(
            @QueryParam("simple") String simple,
            @QueryParam("full") String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(simple != null){
            queryParams.put("simple",simple);
        }
        if(full != null){
            queryParams.put("full",full);
        }
        String url = String.format("%s/loadqueue", pathPre);
        return httpMethod.get(url,queryParams);
    }

}







