package io.sugo.http.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.Configure;
import io.sugo.http.ResourcesManager;
import io.sugo.http.resource.Cache.DataCache;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;

@Path("/druid/coordinator/v1/servers")
public class ServersResource extends Resource{

    public ServersResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/servers";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClusterServers(
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
    @Path("/{serverName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServer(
            @PathParam("serverName") String serverName,
            @QueryParam("simple") String simple
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(simple)){
            queryParams.add("simple",simple);
        }
        String url = String.format("%s/%s", pathPre, serverName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{serverName}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerSegments(
            @PathParam("serverName") String serverName,
            @QueryParam("full") String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(full)){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s/segments", pathPre, serverName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{serverName}/segments/{segmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerSegment(
            @PathParam("serverName") String serverName,
            @PathParam("segmentId") String segmentId
    )
    {
        String url = String.format("%s/%s/segments/%s", pathPre, serverName, segmentId);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }
}
