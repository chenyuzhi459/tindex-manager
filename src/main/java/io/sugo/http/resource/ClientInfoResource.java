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
import java.util.Map;


@Path("/druid/v2/datasources")
public class ClientInfoResource extends Resource{


    public ClientInfoResource() throws IOException {
        ip = configure.getProperty("config.properties","broker_ip");
        pathPre = "http://" + ip + "/druid/v2/datasources";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataSources()
    {
        String url = String.format("%s", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
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
        if(StringUtils.isNotBlank(interval)){
            queryParams.add("interval",interval);
        }
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        if(StringUtils.isNotBlank(interval)){
            queryParams.add("interval",interval);
        }
        String url = String.format("%s/%s/dimensions", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        if(StringUtils.isNotBlank(interval)){
            queryParams.add("interval",interval);
        }
        String url = String.format("%s/%s/metrics", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        if(StringUtils.isNotBlank(intervals)){
            queryParams.add("intervals",intervals);
        }
        queryParams.add("numCandidates",numCandidates);
        String url = String.format("%s/%s/candidates", pathPre, datasource);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

}

