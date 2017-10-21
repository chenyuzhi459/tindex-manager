package io.sugo.http.resource.systemResource;


import io.sugo.http.Configure;
import io.sugo.http.resource.Resource;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Map;
import java.util.Properties;

@Path("/druid/systemConfig")
public class SystemConfigResource extends Resource {
    private static final Logger LOG = Logger.getLogger(SystemConfigResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProperties() {
        LOG.info("get AllProperties");
        return Response.ok().entity(configure.getAllProperties()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProperties(
            final Map<String, String> properties
    ) throws IOException, InterruptedException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/config/config.properties")));
        updatePropertiesByPropertyName("config", properties);
        configure = new Configure();
        return Response.ok().build();
    }

    public void updatePropertiesByPropertyName(String propertyName,Map<String, String> properties) throws IOException {
        String filePath = "src/main/resources/config/" + propertyName + ".properties";
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
        Properties props = configure.getProperties(propertyName);
        for(String key : properties.keySet()) {
            props.setProperty(key, properties.get(key));
        }
        LOG.info("updateProperties new:" + props);
        props.store(bw, "");
        configure = new Configure();
    }
}
