package io.sugo.http.resource.broker;

import com.fasterxml.jackson.jaxrs.smile.SmileMediaTypes;
import com.google.common.collect.Maps;

import io.sugo.http.resource.ForwardResource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;


@Path("/druid/v2/")
public class BrokerQueryResource extends ForwardResource {


    public BrokerQueryResource() throws IOException {
        ip = configure.getProperty("druid.properties","broker.ip");
        pathPre = "http://" + ip + "/druid/v2";
    }

    @POST
    @Path("/candidates")
    @Produces({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    @Consumes({MediaType.APPLICATION_JSON, SmileMediaTypes.APPLICATION_JACKSON_SMILE})
    public Response getQueryTargets(
            final String in,
            @QueryParam("pretty") String pretty,
            @QueryParam("numCandidates") @DefaultValue("-1") int numCandidates,
            @Context final HttpServletRequest req
    )
    {
        Map<String,Object> queryParams = Maps.newHashMap();
        if(pretty != null){
            queryParams.put("pretty",pretty);
        }
        queryParams.put("numCandidates",numCandidates);

        String url = String.format("%s/candidates", pathPre);
        return httpMethod.post(url, in, queryParams);
    }

}

