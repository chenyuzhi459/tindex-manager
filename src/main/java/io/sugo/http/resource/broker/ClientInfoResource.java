package io.sugo.http.resource.broker;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.resource.ForwardResource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(interval != null){
            queryParams.add("interval",interval);
        }
        if(full != null){
            queryParams.add("full",full);
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(interval != null){
            queryParams.add("interval",interval);
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(interval != null){
            queryParams.add("interval",interval);
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(intervals != null){
            queryParams.add("intervals",intervals);
        }
        queryParams.add("numCandidates",numCandidates);
        String url = String.format("%s/%s/candidates", pathPre, datasource);
        return httpMethod.get(url,queryParams);
    }

}

