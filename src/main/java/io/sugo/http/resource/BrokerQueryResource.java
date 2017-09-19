package io.sugo.http.resource;

import com.fasterxml.jackson.jaxrs.smile.SmileMediaTypes;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/v2/")
public class BrokerQueryResource extends Resource{


    public BrokerQueryResource() throws IOException {
        ip = configure.getProperty("config.properties","broker_ip");
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
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        if(pretty != null){
            queryParams.add("pretty",pretty);
        }
        queryParams.add("numCandidates",numCandidates);

        String url = String.format("%s/candidates", pathPre);
        return httpMethod.post(url, in, queryParams);
    }

}

