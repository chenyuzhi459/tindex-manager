package io.sugo.http.resource;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.spi.container.ResourceFilters;
import io.sugo.http.ResourcesManager;
import io.sugo.http.audit.AuditManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.Set;

@Path("/druid/indexer/v1")
public class OverlordResource extends Resource{

    public OverlordResource() throws IOException {
        ip = configure.getProperty("config.properties","overlord_ip");
        pathPre = "http://" + ip + "/druid/indexer/v1";
    }


    @POST
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response taskPost(
            final String taskSpec,
            @Context final HttpServletRequest req
    )
    {
        String url = String.format("%s/task", pathPre);
        String result = httpMethod.post(url,taskSpec);
        return Response.ok(result).build();
    }

    @GET
    @Path("/leader")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeader() {
        String url = String.format("%s/leader", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/task/{taskid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskPayload(@PathParam("taskid") String taskid)
    {
//        localhost:6660/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc
//        192.168.0.225:8090/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc
        String url = String.format("%s/task/%s", pathPre,taskid);
        String result = httpMethod.get(url);
        return Response.ok(result).build();

    }

    @GET
    @Path("/task/{taskid}/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskStatus(@PathParam("taskid") String taskid)
    {
//        localhost:6660/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc/status
        String url = String.format("%s/task/%s/status", pathPre,taskid);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/task/{taskid}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskSegments(@PathParam("taskid") String taskid)
    {
//        localhost:6660/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc/segments
        String url = String.format("%s/task/%s/segments", pathPre,taskid);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }


    //uncheck edit
    @POST
    @Path("/task/{taskid}/shutdown")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doShutdown(@PathParam("taskid") final String taskid) {
        String url = String.format("%s/task/%s/shutdown", pathPre, taskid);
        String result = httpMethod.post(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/worker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkerConfig()
    {
        String url = String.format("%s/worker", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    //uncheck   not final
    @POST
    @Path("/worker")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setWorkerConfig(
            final String workerBehaviorConfig,
            @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
            @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment,
            @Context final HttpServletRequest req
    ){
        String url = String.format("%s/worker", pathPre);

        MultivaluedMapImpl headerParams = new MultivaluedMapImpl();

        if(StringUtils.isNotBlank(author)) {
            headerParams.add("AuditManager.X_DRUID_AUTHOR",author);
        }
        if(StringUtils.isNotBlank(comment)) {
            headerParams.add("AuditManager.X_DRUID_COMMENT",comment);
        }

        String result = httpMethod.post(url,workerBehaviorConfig);

        return Response.ok(result).build();
    }


    @GET
    @Path("/worker/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkerConfigHistory(
            @QueryParam("interval") final String interval,
            @QueryParam("count") final Integer count
    )
    {
        String url = String.format("%s/worker/history", pathPre);
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();

        if(StringUtils.isNotBlank(interval)) {
            queryParams.add("interval",interval);
        }
        if(StringUtils.isNotBlank(interval)) {
            queryParams.add("count",count);
        }

        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }

    //uncheck edit
    @POST
    @Path("/action")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doAction(final String action)
    {
        String url = String.format("%s/action", pathPre);
        String result = httpMethod.post(url,action);
        return Response.ok(result).build();
    }

    @GET
    @Path("/waitingTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWaitingTasks(@Context final HttpServletRequest req) {

        String url = String.format("%s/waitingTasks", pathPre);
//        String token = (String)req.getAttribute("Druid-Auth-Token");
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/pendingTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingTasks(@Context final HttpServletRequest req){
        String url = String.format("%s/pendingTasks", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/runningTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRunningTasks(@Context final HttpServletRequest req)
    {
        String url = String.format("%s/runningTasks", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/completeTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteTasks(@Context final HttpServletRequest req)
    {
        String url = String.format("%s/completeTasks", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/workers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkers() {
        String url = String.format("%s/workers", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/scaling")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScalingState()
    {
        String url = String.format("%s/scaling", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }


    @GET
    @Path("/task/{taskid}/log")
    @Produces("text/plain")
    public Response doGetLog(
            @PathParam("taskid") final String taskid,
            @QueryParam("offset") @DefaultValue("0") long offset
    )
    {
        String url = String.format("%s/task/%s/log", pathPre , taskid);
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("offset",offset);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }


}
