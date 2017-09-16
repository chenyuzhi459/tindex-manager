package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.spi.container.ResourceFilters;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/coordinator/v1/datasources")
public class DataSourcesResource extends Resource{


    public DataSourcesResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/datasources";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueryableDataSources(
            @QueryParam("full") String full,
            @QueryParam("simple") String simple
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(simple)){
            queryParams.add("simple",simple);
        }
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s", pathPre);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTheDataSource(
            @PathParam("dataSourceName") final String dataSourceName,
            @QueryParam("full") final String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @POST
    @Path("/{dataSourceName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enableDataSource(
            @PathParam("dataSourceName") final String dataSourceName
    )
    {
        String url = String.format("%s/%s", pathPre, dataSourceName);
        String result = httpMethod.post(url);
        return Response.ok(result).build();
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(kill)){
            queryParams.add("kill",kill);
        }
        if(StringUtils.isNotBlank(interval)){
            queryParams.add("interval",interval);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        String result = httpMethod.delete(url,queryParams);
        return Response.ok(result).build();
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
        String result = httpMethod.delete(url);
        return Response.ok(result).build();
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(simple)){
            queryParams.add("simple",simple);
        }
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s/intervals", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(simple)){
            queryParams.add("simple",simple);
        }
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s/intervals/%s", pathPre, dataSourceName, interval);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{dataSourceName}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceSegments(
            @PathParam("dataSourceName") String dataSourceName,
            @QueryParam("full") String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s/segments", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{dataSourceName}/segments/{segmentId}")
    public Response deleteDatasourceSegment(
            @PathParam("dataSourceName") String dataSourceName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/%s/segments/%s", pathPre, dataSourceName, segmentId);
        String result = httpMethod.delete(url);
        return Response.ok(result).build();
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
        String result = httpMethod.post(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{dataSourceName}/tiers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSegmentDataSourceTiers(
            @PathParam("dataSourceName") String dataSourceName
    )
    {
        String url = String.format("%s/tiers", pathPre, dataSourceName);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("partial",partial);
        String url = String.format("%s/%s/intervals/%s/serverview", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }
}

