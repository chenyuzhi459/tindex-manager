package io.sugo.http.resource.broker;

import com.google.common.collect.Maps;

import io.sugo.http.resource.ForwardResource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/druid/v2/datasources")
public class ClientInfoResource extends ForwardResource {


    public ClientInfoResource() throws IOException {
        ip = configure.getProperty("druid.properties","broker.ip");
        pathPre = "http://" + ip + "/druid/v2/datasources";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataSources()
    {
        String url = String.format("%s", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasource(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("interval") String interval,
            @QueryParam("full") String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(interval != null){
            queryParams.put("interval",interval);
        }
        if(full != null){
            queryParams.put("full",full);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}/dimensions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasourceDimensions(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("interval") String interval
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(interval != null){
            queryParams.put("interval",interval);
        }
        String url = String.format("%s/%s/dimensions", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}/metrics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasourceMetrics(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("interval") String interval
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(interval != null){
            queryParams.put("interval",interval);
        }
        String url = String.format("%s/%s/metrics", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}/candidates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueryTargets(
            @PathParam("dataSourceName") String datasource,
            @QueryParam("intervals") @DefaultValue("1000/3000") String intervals,
            @QueryParam("numCandidates") @DefaultValue("-1") int numCandidates
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(intervals != null){
            queryParams.put("intervals",intervals);
        }
        queryParams.put("numCandidates",numCandidates);
        String url = String.format("%s/%s/candidates", pathPre, datasource);
        return httpMethod.get(url,queryParams);
    }

}

