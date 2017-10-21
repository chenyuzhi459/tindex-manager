package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/druid/coordinator/v1/servers")
public class ServersResource extends Resource{

    public ServersResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/servers";
    }

    @GET
    @Path("/datasources/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTheDataSourceServers(
            @PathParam("dataSourceName") final String dataSourceName
    )
    {
        String url = String.format("%s/datasources/%s", pathPre, dataSourceName);
        return httpMethod.get(url);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClusterServers(
            @QueryParam("full") String full,
            @QueryParam("simple") String simple
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(simple != null){
            queryParams.add("simple",simple);
        }
        if(full != null){
            queryParams.add("full",full);
        }
        String url = String.format("%s", pathPre);
        return httpMethod.get(url,queryParams);
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
        if(simple != null){
            queryParams.add("simple",simple);
        }
        String url = String.format("%s/%s", pathPre, serverName);
        return httpMethod.get(url,queryParams);
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
        if(full != null){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s/segments", pathPre, serverName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/{serverName}/segments/sortAndSearch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerSegments(
            @PathParam("serverName") String serverName,
            @QueryParam("full") String full,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDscending,
            @QueryParam("searchValue") String searchValue
    ){
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDscending);
        String url = String.format("%s/%s/segments/sortAndSearch", pathPre, serverName);
        return httpMethod.get(url,queryParams);
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
        return httpMethod.get(url);
    }
}
