package io.sugo.http.resource.coordinator;

import com.google.common.collect.Maps;

import io.sugo.http.audit.AuditManager;
import io.sugo.http.resource.ForwardResource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/druid/coordinator/v1/rules")
public class RulesResource extends ForwardResource {


    public RulesResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator.ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/rules";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRules()
    {
        String url = String.format("%s", pathPre);
        return httpMethod.get(url);
    }

    @GET
    @Path("/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasourceRules(
            @PathParam("dataSourceName") final String dataSourceName,
            @QueryParam("full") final String full
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(full != null){
            queryParams.put("full",full);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @POST
    @Path("/{dataSourceName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setDatasourceRules(
            @PathParam("dataSourceName") final String dataSourceName,
            final String rules,
            @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
            @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment,
            @Context HttpServletRequest req
    )
    {
        String url = String.format("%s/%s", pathPre, dataSourceName);
        return httpMethod.post(url,rules);
    }


    @GET
    @Path("/{dataSourceName}/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasourceRuleHistory(
            @PathParam("dataSourceName") final String dataSourceName,
            @QueryParam("interval") final String interval,
            @QueryParam("count") final Integer count
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(interval != null){
            queryParams.put("full",interval);
        }
        if(count != null) {
            queryParams.put("count",count);
        }
        String url = String.format("%s/%s/history", pathPre, dataSourceName);
        return httpMethod.get(url,queryParams);
    }

    @GET
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasourceRuleHistory(
            @QueryParam("interval") final String interval,
            @QueryParam("count") final Integer count
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(interval != null){
            queryParams.put("full",interval);
        }
        if(count != null) {
            queryParams.put("count",count);
        }
        String url = String.format("%s/history", pathPre);
        return httpMethod.get(url,queryParams);
    }
}

