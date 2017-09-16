package io.sugo.http.resource;

import com.fasterxml.jackson.jaxrs.smile.SmileMediaTypes;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.audit.AuditManager;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


@Path("/druid/coordinator/v1/lookups")
public class LookupCoordinatorResource extends Resource{


    public LookupCoordinatorResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/lookups";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response getTiers(
            @DefaultValue("false") @QueryParam("discover") boolean discover
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("discover",discover);

        String url = String.format("%s", pathPre);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    //not finish
    @POST
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Consumes({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response updateAllLookups(
            final String in,
            @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
            @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment
    )
    {
        String url = String.format("%s", pathPre);
        String result = httpMethod.post(url);
        return Response.ok(result).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}/{lookup}")
    public Response deleteLookup(
            @PathParam("tier") String tier,
            @PathParam("lookup") String lookup,
            @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
            @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment
    )
    {
        String url = String.format("%s/%s/%s", pathPre, tier, lookup);
        String result = httpMethod.delete(url);
        return Response.ok(result).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}/{lookup}")
    public Response createOrUpdateLookup(
            @PathParam("tier") String tier,
            @PathParam("lookup") String lookup,
            @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
            @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment,
            InputStream in,
            @Context HttpServletRequest req
    )
    {
        String url = String.format("%s/%s/%s", pathPre, tier, lookup);
        String result = httpMethod.post(url);
        return Response.ok(result).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}/{lookup}")
    public Response getSpecificLookup(
            @PathParam("tier") String tier,
            @PathParam("lookup") String lookup
    )
    {
        String url = String.format("%s/%s/%s", pathPre, tier, lookup);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}")
    public Response getSpecificTier(
            @PathParam("tier") String tier
    )
    {
        String url = String.format("%s/%s", pathPre, tier);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }
}

