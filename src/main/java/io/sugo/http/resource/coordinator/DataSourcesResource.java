package io.sugo.http.resource.coordinator;

import com.google.common.collect.Maps;
import io.sugo.http.resource.ForwardResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/druid/coordinator/v1/datasources")
public class DataSourcesResource extends ForwardResource {


    public DataSourcesResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator.ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/datasources";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueryableDataSources(
            @QueryParam("searchValue") @DefaultValue("") String searchValue,
            @QueryParam("full") String full,
            @QueryParam("simple") String simple,
            @QueryParam("isDescending") @DefaultValue("true") boolean isDescending
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        queryParams.put("isDescending", isDescending);
        if(simple != null) {
            queryParams.put("simple",simple);
        }
        if(full != null) {
            queryParams.put("full",full);
        }
        if(searchValue != null) {
            queryParams.put("searchValue",searchValue);
        }
        String url = String.format("%s", pathPre);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTheDataSource(
            @PathParam("dataSourceName") final String dataSourceName,
            @QueryParam("full") final String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(full != null){
            queryParams.put("full",full);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @POST
    @Path("/{dataSourceName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enableDataSource(
            @PathParam("dataSourceName") final String dataSourceName
    )
    {
        String url = String.format("%s/%s", pathPre, dataSourceName);
        return httpMethod.post(url);
    }

    @DELETE
    @Deprecated
    @Path("/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDataSource(
            @PathParam("dataSourceName") final String dataSourceName,
            @QueryParam("kill") final String kill,
            @QueryParam("interval") final String interval
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(kill != null){
            queryParams.put("kill",kill);
        }
        if(interval != null){
            queryParams.put("interval",interval);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        return httpMethod.delete(url,queryParams);
    }

    @DELETE
    @Path("/{dataSourceName}/intervals/{interval}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDataSourceSpecificInterval(
            @PathParam("dataSourceName") final String dataSourceName,
            @PathParam("interval") final String interval
    )
    {
        String url = String.format("%s/%s/intervals/%s", pathPre, dataSourceName, interval);
        return httpMethod.delete(url);
    }


    @GET
    @Path("/{dataSourceName}/intervals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceIntervals(
            @PathParam("dataSourceName") String dataSourceName,
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
        String url = String.format("%s/%s/intervals", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}/intervals/{interval}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceSpecificInterval(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("interval") String interval,
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
        String url = String.format("%s/%s/intervals/%s", pathPre, dataSourceName, interval);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceSegments(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("full") String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(full != null){
            queryParams.put("full",full);
        }
        String url = String.format("%s/%s/segments", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{dataSourceName}/segments/{segmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/%s/segments/%s", pathPre, dataSourceName, segmentId);
        return httpMethod.get(url);
    }

    @DELETE
    @Path("/{dataSourceName}/segments/{segmentId}")
    public Response deleteDatasourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/%s/segments/%s", pathPre, dataSourceName, segmentId);
        return httpMethod.delete(url);
    }

    @POST
    @Path("/{dataSourceName}/segments/{segmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enableDatasourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/%s/segments/%s", pathPre, dataSourceName, segmentId);
        return httpMethod.post(url);
    }

    @GET
    @Path("/{dataSourceName}/tiers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceTiers(
            @PathParam("dataSourceName") String dataSourceName
    )
    {
        String url = String.format("%s/tiers", pathPre, dataSourceName);
        return httpMethod.get(url);
    }

    @GET
    @Path("/{dataSourceName}/intervals/{interval}/serverview")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceSpecificInterval(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("interval") String interval,
            @QueryParam("partial") final boolean partial
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        queryParams.put("partial",partial);
        String url = String.format("%s/%s/intervals/%s/serverview", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }
}

