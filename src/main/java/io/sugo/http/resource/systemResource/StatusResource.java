package io.sugo.http.resource.systemResource;

import io.sugo.http.resource.Resource;
import io.sugo.http.util.HttpMethodProxy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/status")
public class StatusResource extends Resource {


    public StatusResource() throws IOException {
        ip = configure.getProperty("druid.properties","broker_ip");
        pathPre = "http://" + ip + "/status";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response status() {

        String url = String.format("%s", pathPre);
        return httpMethod.get(url);
    }


}

