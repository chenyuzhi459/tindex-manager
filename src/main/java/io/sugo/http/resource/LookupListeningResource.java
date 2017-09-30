package io.sugo.http.resource;

import com.fasterxml.jackson.jaxrs.smile.SmileMediaTypes;
import com.google.common.base.Strings;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


@Path("/druid/listen/v1/lookups")
public class LookupListeningResource extends Resource{

    public LookupListeningResource() throws IOException {
        pathPre = "/druid/listen/v1/lookups";
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Consumes({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response serviceAnnouncementPOSTAll(
            final String data,
            final @Context HttpServletRequest req,
            @QueryParam("ip") String ip
    ){
        if(Strings.isNullOrEmpty(ip)) {
            return Response.status(400).build();
        }
        String url = String.format("http://%s%s", ip, pathPre);
        return httpMethod.post(url, data);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response getAll(
            @QueryParam("searchValue") String searchValue,
            @QueryParam("isDescending") @DefaultValue("false") boolean isDescending,
            @QueryParam("ip") String ip
    )
    {
        if(Strings.isNullOrEmpty(ip)) {
            return Response.status(400).build();
        }

        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(searchValue != null){
            queryParams.add("searchValue",searchValue);
        }
        queryParams.add("isDescending", isDescending);

        String url = String.format("http://%s%s", ip, pathPre);
        return httpMethod.get(url, queryParams);
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response serviceAnnouncementGET(
            final @PathParam("id") String id,
            @QueryParam("ip") String ip
    )
    {
        if(Strings.isNullOrEmpty(ip)) {
            return Response.status(400).build();
        }
        String url = String.format("http://%s%s/%s", ip, pathPre,id);
        return httpMethod.get(url);
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response serviceAnnouncementDELETE(
            final @PathParam("id") String id,
            @QueryParam("ip") String ip
    )
    {
        if(Strings.isNullOrEmpty(ip)) {
            return Response.status(400).build();
        }
        String url = String.format("http://%s%s/%s", ip, pathPre,id);
        return httpMethod.delete(url);
    }

    @Path("/{id}")
    @POST
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response serviceAnnouncementPOST(
            final @PathParam("id") String id,
            final String data,
            final @Context HttpServletRequest req,
            @QueryParam("ip") String ip
    )
    {
        if(Strings.isNullOrEmpty(ip)) {
            return Response.status(400).build();
        }
        String url = String.format("http://%s%s/%s", ip, pathPre,id);
        return httpMethod.post(url, data);
    }

}

