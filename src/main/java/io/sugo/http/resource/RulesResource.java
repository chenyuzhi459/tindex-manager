package io.sugo.http.resource;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sugo.http.audit.AuditManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/coordinator/v1/rules")
public class RulesResource extends Resource{


    public RulesResource() throws IOException {
        ip = configure.getProperty("config.properties","coordinator_ip");
        pathPre = "http://" + ip + "/druid/coordinator/v1/rules";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRules()
    {
        String url = String.format("%s", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }

    @GET
    @Path("/{dataSourceName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasourceRules(
            @PathParam("dataSourceName") final String dataSourceName,
            @QueryParam("full") final String full
    )
    {
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(full != null){
            queryParams.add("full",full);
        }
        String url = String.format("%s/%s", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
        String result = httpMethod.post(url);
        return Response.ok(result).build();
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(interval != null){
            queryParams.add("full",interval);
        }
        if(count != null) {
            queryParams.add("count",count);
        }
        String url = String.format("%s/%s/history", pathPre, dataSourceName);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
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
            queryParams.add("full",interval);
        }
        if(count != null) {
            queryParams.add("count",count);
        }
        String url = String.format("%s/history", pathPre);
        String result = httpMethod.get(url,queryParams);
        return Response.ok(result).build();
    }
}

