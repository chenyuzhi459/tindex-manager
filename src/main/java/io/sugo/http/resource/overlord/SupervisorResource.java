package io.sugo.http.resource.overlord;

import com.google.common.collect.Maps;

import io.sugo.http.resource.ForwardResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/druid/indexer/v1/supervisor")
public class SupervisorResource extends ForwardResource {

    public SupervisorResource() throws IOException {
        ip = configure.getProperty("druid.properties","overlord.ip");
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
    public Response specGetAll(
            @QueryParam("full") String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(null != full){
            queryParams.put("full",full);
        }
        String url = String.format("%s", pathPre);
        return httpMethod.getDirectly(url,queryParams);
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
    @Path("/history/part")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorHistoryNum(
            @QueryParam("searchDimension1") final String searchDimension1, //defaultValue: spec_id
            @QueryParam("searchValue1")  @DefaultValue("") final String searchValue1,
            @QueryParam("searchDimension2") final String searchDimension2, //defaultValue: payload/created_date
            @QueryParam("searchValue2") @DefaultValue("") final String searchValue2,
            @QueryParam("sortDimension") @DefaultValue("created_date") final String sortDimension,
            @QueryParam("isDescending") @DefaultValue("true") final boolean isDescending,
            @QueryParam("offset") @DefaultValue("0") final int offset,
            @QueryParam("size") @DefaultValue("10") final int size
    ){
        Map<String,Object> queryParams = Maps.newHashMap();
        if(null != searchDimension1){
            queryParams.put("searchDimension1",searchDimension1);
        }
        if(null != searchDimension2){
            queryParams.put("searchDimension2",searchDimension2);
        }
        queryParams.put("searchValue1",searchValue1);
        queryParams.put("searchValue2",searchValue2);
        queryParams.put("sortDimension",sortDimension);
        queryParams.put("isDescending",isDescending);
        queryParams.put("offset",offset);
        queryParams.put("size",size);

        String url = String.format("%s/history/part", pathPre);

        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/history/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorHistoryNum(
            @QueryParam("searchDimension1") final String searchDimension1, //defaultValue: spec_id
            @QueryParam("searchValue1")  @DefaultValue("") final String searchValue1,
            @QueryParam("searchDimension2") final String searchDimension2, //defaultValue: payload/created_date
            @QueryParam("searchValue2") @DefaultValue("") final String searchValue2
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(null != searchDimension1){
            queryParams.put("searchDimension1",searchDimension1);
        }
        if(null != searchDimension2){
            queryParams.put("searchDimension2",searchDimension2);
        }
        queryParams.put("searchValue1",searchValue1);
        queryParams.put("searchValue2",searchValue2);
        String url = String.format("%s/history/count", pathPre);
        return httpMethod.get(url,queryParams);
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

