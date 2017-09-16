package io.sugo.http.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


@Path("/status")
public class StatusResource extends Resource{


    public StatusResource() throws IOException {
        ip = configure.getProperty("config.properties","broker_ip");
        pathPre = "http://" + ip + "/status";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response status() {

        String url = String.format("%s", pathPre);
        String result = httpMethod.get(url);
        return Response.ok(result).build();
    }


}

