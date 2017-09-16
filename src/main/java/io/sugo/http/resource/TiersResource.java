package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/coordinator/v1/tiers")
public class TiersResource extends Resource{


    public TiersResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/tiers";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTiers(
            @QueryParam("simple") String simple
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(simple)){
            queryParams.add("simple",simple);
        }
        String url = String.format("%s", pathPre);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{tierName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTierDatasources(
            @PathParam("tierName") String tierName,
            @QueryParam("simple") String simple
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(StringUtils.isNotBlank(simple)){
            queryParams.add("simple",simple);
        }
        String url = String.format("%s/%s", pathPre, tierName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

}

