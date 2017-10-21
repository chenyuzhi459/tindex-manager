package io.sugo.http.resource.overlordResource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.audit.AuditManager;
import io.sugo.http.resource.Resource;
import io.sugo.http.util.HttpMethodProxy;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;

@Path("/druid/indexer/v1")
public class OverlordResource extends Resource {

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
    @Path("/{supervisorId}/waitingTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorWaitingTasks(
            @PathParam("supervisorId") final String supervisorId,
            @Context final HttpServletRequest req)
    {
        String url = String.format("%s/%s/waitingTasks", pathPre,supervisorId);
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
    @Path("/{supervisorId}/pendingTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorPendingTasks(
            @PathParam("supervisorId") final String supervisorId,
            @Context final HttpServletRequest req)
    {
        String url = String.format("%s/%s/pendingTasks", pathPre,supervisorId);
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
    @Path("/{supervisorId}/runningTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorRunningTasks(
            @PathParam("supervisorId") final String supervisorId,
            @Context final HttpServletRequest req)
    {
        String url = String.format("%s/%s/runningTasks", pathPre,supervisorId);
        return httpMethod.get(url);
    }

    @GET
    @Path("/completeTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteTasks(
            @QueryParam("offset") @DefaultValue("0") final int offset,
            @QueryParam("size") @DefaultValue("-1") final int size,
            @QueryParam("sortDimension") @DefaultValue("createdTime") final String sortDimension,
            @QueryParam("isDescending") @DefaultValue("true") final String isDescending,
            @Context final HttpServletRequest req
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("offset",offset);
        queryParams.add("size",size);
        if(sortDimension != null){
            queryParams.add("sortDimension",sortDimension);
        }
        queryParams.add("isDescending",isDescending);

        String url = String.format("%s/completeTasks", pathPre);

        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/completeTasks/custom/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteTasks(
            @QueryParam("searchDimension1") final String searchDimension1,
            @QueryParam("searchValue1") @DefaultValue("") final String searchValue1,
            @QueryParam("searchDimension2") final String searchDimension2,
            @QueryParam("searchValue2") @DefaultValue("") final String searchValue2,
            @QueryParam("searchDimension3") final String searchDimension3, //defaultValue: payload
            @QueryParam("searchValue3") @DefaultValue("") final String searchValue3,
            @QueryParam("sortDimension") @DefaultValue("created_date") final String sortDimension,
            @QueryParam("isDescending") @DefaultValue("true") final boolean isDescending,
            @QueryParam("offset") @DefaultValue("0") final int offset,
            @QueryParam("size") @DefaultValue("10") final int size,
            @Context final HttpServletRequest req)
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(null != searchDimension1){
            queryParams.add("searchDimension1",searchDimension1);
        }
        if(null != searchDimension2){
            queryParams.add("searchDimension2",searchDimension2);
        }
        if(null != searchDimension3){
            queryParams.add("searchDimension3",searchDimension3);
        }
        queryParams.add("searchValue1",searchValue1);
        queryParams.add("searchValue2",searchValue2);
        queryParams.add("searchValue3",searchValue3);
        queryParams.add("sortDimension",sortDimension);
        queryParams.add("isDescending",isDescending);
        queryParams.add("offset",offset);
        queryParams.add("size",size);

        String url = String.format("%s/completeTasks/custom/list", pathPre);

        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/completeTasks/custom/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteTasksNum(
            @QueryParam("searchDimension1") final String searchDimension1, //defaultValue: id
            @QueryParam("searchValue1")  @DefaultValue("") final String searchValue1,
            @QueryParam("searchDimension2") final String searchDimension2, //defaultValue: status_payload
            @QueryParam("searchValue2") @DefaultValue("") final String searchValue2,
            @QueryParam("searchDimension3") final String searchDimension3, //defaultValue: payload
            @QueryParam("searchValue3") @DefaultValue("") final String searchValue3,
            @Context final HttpServletRequest req
    ){
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(null != searchDimension1){
            queryParams.add("searchDimension1",searchDimension1);
        }
        if(null != searchDimension2){
            queryParams.add("searchDimension2",searchDimension2);
        }
        if(null != searchDimension3){
            queryParams.add("searchDimension3",searchDimension3);
        }
        queryParams.add("searchValue1",searchValue1);
        queryParams.add("searchValue2",searchValue2);
        queryParams.add("searchValue3",searchValue3);
        String url = String.format("%s/completeTasks/custom/count", pathPre);
        return httpMethod.get(url,queryParams);
    }



    @GET
    @Path("/completeTasks/{supervisorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInactiveSupervisorTasks(
            @PathParam("supervisorId") final String supervisorId,
            @QueryParam("searchDimension1") final String searchDimension1,
            @QueryParam("searchValue1") @DefaultValue("") final String searchValue1,
            @QueryParam("searchDimension2") final String searchDimension2,
            @QueryParam("searchValue2") @DefaultValue("") final String searchValue2,
            @QueryParam("sortDimension") @DefaultValue("created_date") final String sortDimension,
            @QueryParam("isDescending") @DefaultValue("true") final boolean isDescending,
            @QueryParam("offset") @DefaultValue("0") final int offset,
            @QueryParam("size") @DefaultValue("10") final int size,
            @Context final HttpServletRequest req)
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(null != searchDimension1){
            queryParams.add("searchDimension1",searchDimension1);
        }
        if(null != searchDimension2){
            queryParams.add("searchDimension2",searchDimension2);
        }
        queryParams.add("searchValue1",searchValue1);
        queryParams.add("searchValue2",searchValue2);
        queryParams.add("sortDimension",sortDimension);
        queryParams.add("isDescending",isDescending);
        queryParams.add("offset",offset);
        queryParams.add("size",size);

        String url = String.format("%s/completeTasks/%s", pathPre,supervisorId);

        return httpMethod.get(url,queryParams);

    }

    @GET
    @Path("/completeTasks/{supervisorId}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorCompleteTasksNum(
            @PathParam("supervisorId") final String supervisorId,
            @QueryParam("searchDimension1") final String searchDimension1, //defaultValue: id
            @QueryParam("searchValue1")  @DefaultValue("") final String searchValue1,
            @QueryParam("searchDimension2") final String searchDimension2, //defaultValue: status_payload
            @QueryParam("searchValue2") @DefaultValue("") final String searchValue2,
            @Context final HttpServletRequest req)
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(null != searchDimension1){
            queryParams.add("searchDimension1",searchDimension1);
        }
        if(null != searchDimension2){
            queryParams.add("searchDimension2",searchDimension2);
        }
        queryParams.add("searchValue1",searchValue1);
        queryParams.add("searchValue2",searchValue2);
        String url = String.format("%s/completeTasks/%s/count", pathPre,supervisorId);
        return httpMethod.get(url,queryParams);
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
