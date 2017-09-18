package io.sugo.http.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/indexer/v1/supervisor")
public class SupervisorResource extends Resource{

    public SupervisorResource() throws IOException {
        ip = configure.getProperty("config.properties","overlord_ip");
        pathPre = "http://" + ip + "/druid/indexer/v1/supervisor";
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response specPost(final String spec)
    {
        String url = String.format("%s", pathPre);
        return httpMethod.post(url,spec);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response specGetAll()
    {
        String url = String.format("%s", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response specGet(@PathParam("id") final String id)
    {
        String url = String.format("%s/%s", pathPre , id);
        return httpMethod.get(url);
    }

    @GET
    @Path("/{id}/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response specGetStatus(@PathParam("id") final String id)
    {
        String url = String.format("%s/%s/status", pathPre , id);
        return httpMethod.get(url);
    }

    @POST
    @Path("/{id}/shutdown")
    @Produces(MediaType.APPLICATION_JSON)
    public Response shutdown(@PathParam("id") final String id)
    {
        String url = String.format("%s/%s/shutdown", pathPre , id);
        return httpMethod.post(url);
    }

    @GET
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response specGetAllHistory()
    {
        String url = String.format("%s/history", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/{id}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response specGetHistory(@PathParam("id") final String id)
    {
        String url = String.format("%s/%s/history", pathPre , id);
        return httpMethod.get(url);
    }

    @POST
    @Path("/{id}/reset")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reset(@PathParam("id") final String id)
    {
        String url = String.format("%s/%s/reset", pathPre , id);
        return httpMethod.post(url);
    }

    @POST
    @Path("/topic/{id}/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTopic(@PathParam("id") final String id)
    {
        String url = String.format("%s/topic/%s/delete", pathPre , id);
        return httpMethod.post(url);
    }
}

