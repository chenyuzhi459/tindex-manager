package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;

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
            @QueryParam("full") String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        String url = String.format("%s/datasources/%s/segments", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @POST
    @Path("/datasources/{dataSourceName}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSourceSegments(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("full") String full,
            final String intervals
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
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
}

