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


@Path("/druid/coordinator/v1/config")
public class CoordinatorDynamicConfigsResource extends ForwardResource {


    public CoordinatorDynamicConfigsResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator.ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/config";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDynamicConfigs()
    {
        String url = String.format("%s", pathPre);
        return httpMethod.get(url);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setDynamicConfigs(final String dynamicConfig,
                                      @HeaderParam(AuditManager.X_DRUID_AUTHOR) @DefaultValue("") final String author,
                                      @HeaderParam(AuditManager.X_DRUID_COMMENT) @DefaultValue("") final String comment,
                                      @Context HttpServletRequest req
    )
    {
        String url = String.format("%s", pathPre);
        return httpMethod.post(url);
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
            queryParams.put("interval",interval);
        }
        if(count != null){
            queryParams.put("count",count);
        }
        String url = String.format("%s/history", pathPre);
        return httpMethod.get(url,queryParams);
    }

}

