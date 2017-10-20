package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.spi.container.ResourceFilters;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/coordinator/v1/metadata")
public class MetadataResource extends Resource{


    public MetadataResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/metadata";
    }

    @GET
    @Path("/datasources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseDataSources(
            @QueryParam("full") String full,
            @QueryParam("includeDisabled") String includeDisabled
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(includeDisabled != null){
            queryParams.add("includeDisabled",includeDisabled);
        }
        if(full != null){
            queryParams.add("full",full);
        }
        String url = String.format("%s/datasources", pathPre);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/datasources/sortAndSearch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseDataSources(
            @QueryParam("full") String full,
            @QueryParam("simple") String simple,
            @QueryParam("includeDisabled") String includeDisabled,
            @QueryParam("sortDimension") String sortDimension,
            @QueryParam("isDescending") @DefaultValue("true") boolean isDescending,
            @QueryParam("searchValue") String searchValue,
            @Context final HttpServletRequest req
    ){
        String url = String.format("%s/datasources/sortAndSearch", pathPre);
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        if(simple != null){
            queryParams.add("simple",simple);
        }
        if(includeDisabled != null){
            queryParams.add("includeDisabled",includeDisabled);
        }
        if(sortDimension != null){
            queryParams.add("sortDimension",sortDimension);
        }
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDescending);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/datasources/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSource(
            @PathParam("dataSourceName") final String dataSourceName
    )
    {
        String url = String.format("%s/datasources/%s", pathPre,dataSourceName);
        return httpMethod.get(url);
    }

    @GET
    @Path("/datasources/{dataSourceName}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSourceSegments(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("sortDimension") @DefaultValue("id") String sortDimension,   //可取值:id,size
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending,
            @QueryParam("full") String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        if(sortDimension != null){
            queryParams.add("sortDimension",sortDimension);
        }
        queryParams.add("isDescending",isDescending);
        String url = String.format("%s/datasources/%s/segments", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @POST
    @Path("/datasources/{dataSourceName}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSourceSegments(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending,
            @QueryParam("full") String full,
            final String intervals
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDescending);
        String url = String.format("%s/datasources/%s/segments", pathPre, dataSourceName);
        return httpMethod.post(url, intervals, queryParams);
    }

    @GET
    @Path("/datasources/{dataSourceName}/segments/{segmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/datasources/%s/segments/%s", pathPre, dataSourceName, segmentId);
        return httpMethod.get(url);
    }

    @GET
    @Path("/datasources/{dataSourceName}/intervals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSourceIntervals(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("searchDimension") String searchDimension,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("sortDimension") String sortDimension,
            @QueryParam("isDescending")  boolean isDescending,
            @QueryParam("simple") String simple,
            @QueryParam("full") String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        if(simple != null){
            queryParams.add("simple",simple);
        }
        if(searchDimension != null){
            queryParams.add("searchDimension",searchDimension);
        }
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        if(sortDimension != null){
            queryParams.add("sortDimension",sortDimension);
        }
        queryParams.add("isDescending",isDescending);
        String url = String.format("%s/datasources/%s/intervals", pathPre, dataSourceName);
        return httpMethod.get(url, queryParams);
    }

    @DELETE
    @Path("/datasources/{dataSourceName}/segments/{segmentId}/disable")
    public Response disableDatasourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/datasources/%s/segments/%s/disable", pathPre, dataSourceName, segmentId);
        return httpMethod.delete(url);
    }

    @POST
    @Path("/datasources/{dataSourceName}/segments/{segmentId}/enable")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enableDatasourceSegment(
        @PathParam("dataSourceName") String dataSourceName,
        @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/datasources/%s/segments/%s/enable", pathPre, dataSourceName, segmentId);
        return httpMethod.post(url);
    }

    @DELETE
    @Path("/datasources/{dataSourceName}/segments/{segmentId}/delete")
    public Response deleteDatasourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/datasources/%s/segments/%s/delete", pathPre, dataSourceName, segmentId);
        return httpMethod.delete(url);
    }

    @GET
    @Path("/datasources/{dataSourceName}/disableSegments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisableSegmentDataSourceSegments(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending
    ){
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(searchValue != searchValue){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDescending);
        String url = String.format("%s/datasources/%s/disableSegments", pathPre, dataSourceName);
        return httpMethod.get(url, queryParams);
    }

    @GET
    @Path("/disableDatasources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisableDatabaseDataSources(
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending", isDescending);
        String url = String.format("%s/disableDatasources", pathPre);
        return httpMethod.get(url, queryParams);
    }

    @GET
    @Path("/datasources/{dataSourceName}/disableIntervals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSourceDisableIntervals(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("true")  boolean isDescending
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending", isDescending);
        String url = String.format("%s/datasources/%s/disableIntervals", pathPre, dataSourceName);
        return httpMethod.get(url, queryParams);
    }

    @POST
    @Path("/datasources/{dataSourceName}/disableSegments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisableSegmentsByInterval(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("full") String full,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending,
            final String intervals
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDescending);
        String url = String.format("%s/datasources/%s/disableSegments", pathPre, dataSourceName);
        return httpMethod.post(url, intervals, queryParams);
    }

    @DELETE
    @Path("/datasources/{dataSourceName}/disable")
    public Response disableDatasourceSource(
            @PathParam("dataSourceName") String dataSourceName
    )
    {
        String url = String.format("%s/datasources/%s/disable", pathPre, dataSourceName);
        return httpMethod.delete(url);
    }

    @DELETE
    @Path("/datasources/{dataSourceName}/delete")
    public Response deleteDatasourceSource(
            @PathParam("dataSourceName") String dataSourceName
    )
    {
        String url = String.format("%s/datasources/%s/delete", pathPre, dataSourceName);
        return httpMethod.delete(url);
    }


    @POST
    @Path("/datasources/{dataSourceName}/enable")
    public Response enableDatasourceSource(
            @PathParam("dataSourceName") String dataSourceName
    )
    {
        String url = String.format("%s/datasources/%s/enable", pathPre, dataSourceName);
        return httpMethod.post(url);
    }


    @DELETE
    @Path("/datasources/{dataSourceName}/intervals/{interval}/disable")
    public Response disableSegmentByInterval(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("interval") String interval
    )
    {
        String url = String.format("%s/datasources/%s/intervals/%s/disable", pathPre, dataSourceName, interval);
        return httpMethod.delete(url);
    }

    @DELETE
    @Path("/datasources/{dataSourceName}/intervals/{interval}/delete")
    public Response deleteSegmentByInterval(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("interval") String interval
    )
    {
        String url = String.format("%s/datasources/%s/intervals/%s/delete", pathPre, dataSourceName, interval);
        return httpMethod.delete(url);
    }

    @POST
    @Path("/datasources/{dataSourceName}/intervals/{interval}/enable")
    public Response enableSegmentByInterval(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("interval") String interval
    )
    {
        String url = String.format("%s/datasources/%s/intervals/%s/enable", pathPre, dataSourceName, interval);
        return httpMethod.post(url);
    }
}


