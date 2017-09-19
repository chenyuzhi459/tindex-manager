package io.sugo.http.resource;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.audit.AuditManager;
import io.sugo.http.util.HttpMethodProxy;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;

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

        return new HttpMethodProxy(client).post(url,taskSpec);
    }

    @GET
    @Path("/leader")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeader() {
        String url = String.format("%s/leader", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/task/{taskid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskPayload(@PathParam("taskid") String taskid)
    {
//        localhost:6660/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc
//        192.168.0.225:8090/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc
        String url = String.format("%s/task/%s", pathPre,taskid);
        return httpMethod.get(url);

    }

    @GET
    @Path("/task/{taskid}/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskStatus(@PathParam("taskid") String taskid)
    {
//        localhost:6660/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc/status
        String url = String.format("%s/task/%s/status", pathPre,taskid);
        return httpMethod.get(url);
    }

    @GET
    @Path("/task/{taskid}/segments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskSegments(@PathParam("taskid") String taskid)
    {
//        localhost:6660/druid/indexer/v1/task/lucene_index_kafka_wuxianjiRT_5d0772c1d88e862_ebeeegoc/segments
        String url = String.format("%s/task/%s/segments", pathPre,taskid);
        return httpMethod.get(url);
    }


    //uncheck edit
    @POST
    @Path("/task/{taskid}/shutdown")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doShutdown(@PathParam("taskid") final String taskid) {
        String url = String.format("%s/task/%s/shutdown", pathPre, taskid);
        return httpMethod.post(url);
    }

    @GET
    @Path("/worker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkerConfig()
    {
        String url = String.format("%s/worker", pathPre);
        return httpMethod.get(url);
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

        if(author != null) {
            headerParams.add("AuditManager.X_DRUID_AUTHOR",author);
        }
        if(comment != null) {
            headerParams.add("AuditManager.X_DRUID_COMMENT",comment);
        }
        return httpMethod.post(url,workerBehaviorConfig);
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

        if(interval != null) {
            queryParams.add("interval",interval);
        }
        if(count != null) {
            queryParams.add("count",count);
        }
        return httpMethod.get(url,queryParams);
    }

    //uncheck edit
    @POST
    @Path("/action")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doAction(final String action)
    {
        String url = String.format("%s/action", pathPre);
        return httpMethod.post(url,action);
    }

    @GET
    @Path("/waitingTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWaitingTasks(@Context final HttpServletRequest req) {

        String url = String.format("%s/waitingTasks", pathPre);
//        String token = (String)req.getAttribute("Druid-Auth-Token");
        return httpMethod.get(url);
    }

    @GET
    @Path("/pendingTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingTasks(@Context final HttpServletRequest req){
        String url = String.format("%s/pendingTasks", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/runningTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRunningTasks(@Context final HttpServletRequest req)
    {
        String url = String.format("%s/runningTasks", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/completeTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteTasks(@Context final HttpServletRequest req)
    {
        String url = String.format("%s/completeTasks", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/workers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkers() {
        String url = String.format("%s/workers", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/scaling")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScalingState()
    {
        String url = String.format("%s/scaling", pathPre);
        return httpMethod.get(url);
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
        return httpMethod.get(url,queryParams);
    }


}
