package io.sugo.http.resource.brokerResource;

import io.sugo.http.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/druid/worker/v1")
public class WorkerResource extends Resource {


    public WorkerResource() throws IOException {
        ip = configure.getProperty("druid.properties","broker_ip");
        pathPre = "http://" + ip + "/druid/worker/v1";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response status() {

        String url = String.format("%s", pathPre);
        return httpMethod.get(url);
    }


}

