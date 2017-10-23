package io.sugo.http.resource.coordinatorResource;

import com.fasterxml.jackson.jaxrs.smile.SmileMediaTypes;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.audit.AuditManager;
import io.sugo.http.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


@Path("/druid/coordinator/v1/lookups")
public class LookupCoordinatorResource extends Resource {


    public LookupCoordinatorResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator_ip");
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
        return httpMethod.get(url,queryParams);
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
        return httpMethod.post(url);
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
        return httpMethod.delete(url);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}/{lookup}")
    public Response createOrUpdateLookup(
            @PathParam("tier") String tier,
            @PathParam("lookup") String lookup,
            @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
            @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment,
            String data,
            @Context HttpServletRequest req
    )
    {
        String url = String.format("%s/%s/%s", pathPre, tier, lookup);
        return httpMethod.post(url,data);
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
        return httpMethod.get(url);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}")
    public Response getSpecificTier(
            @PathParam("tier") String tier,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending
    )
    {
        String url = String.format("%s/%s", pathPre, tier);
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDescending);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Path("/{tier}/details")
    public Response getSpecificTierDetails(
            @PathParam("tier") String tier,
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending
    )
    {
        String url = String.format("%s/%s/details", pathPre, tier);
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending",isDescending);
        return httpMethod.get(url,queryParams);
    }
}

