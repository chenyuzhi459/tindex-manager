package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.spi.container.ResourceFilters;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


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
        if(StringUtils.isNotBlank(includeDisabled)){
            queryParams.add("includeDisabled",includeDisabled);
        }
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/datasources", pathPre);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @GET
    @Path("/datasources/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatabaseSegmentDataSource(
            @PathParam("dataSourceName") final String dataSourceName
    )
    {
        String url = String.format("%s/datasources/%s", pathPre,dataSourceName);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
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
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/datasources/%s/segments", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/datasources/%s/segments", pathPre, dataSourceName);
        String result = httpMethod.post(url, intervals, queryParams);
        return Response.ok(result).build();
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
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }
}

