package io.sugo.http.resource.coordinatorResource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.audit.AuditManager;
import io.sugo.http.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/coordinator/v1/config")
public class CoordinatorDynamicConfigsResource extends Resource {


    public CoordinatorDynamicConfigsResource() throws IOException {
        ip = configure.getProperty("druid.properties","coordinator_ip");
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(interval != null){
            queryParams.add("interval",interval);
        }
        if(count != null){
            queryParams.add("count",count);
        }
        String url = String.format("%s/history", pathPre);
        return httpMethod.get(url,queryParams);
    }

}

