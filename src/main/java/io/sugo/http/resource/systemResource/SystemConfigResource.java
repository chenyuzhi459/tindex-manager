package io.sugo.http.resource.systemResource;


import com.google.common.cache.LoadingCache;
import io.sugo.cache.Cache;
import io.sugo.http.Configure;
import io.sugo.http.resource.Resource;
import io.sugo.kafka.ConsumerHandler;
import io.sugo.kafka.KafkaHandler;
import io.sugo.kafka.factory.KafkaFactory;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Path("/druid/systemConfig")
public class SystemConfigResource extends Resource {
    private static final Logger LOG = Logger.getLogger(SystemConfigResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProperties() {
        LOG.info("get AllProperties");
        return Response.ok().entity(configure.getAllPropertiesMap()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProperties(
            final Map<String, Map<String, String>> allProperties
    ) throws IOException, InterruptedException {

        Iterator<String> it = allProperties.keySet().iterator();
        while (it.hasNext()) {
            String propName = it.next();
            Map<String, String> propertiesMap = allProperties.get(propName);
            updatePropertiesByPropertyName(propName.replaceAll("_","."), propertiesMap);
        }

        Configure newConfigure = configure.reload();
        this.reloadActions(newConfigure);

        return Response.ok().build();
    }

    public void reloadActions(Configure newConfigure) {
        Resource.configure = newConfigure;
        KafkaHandler.configure = newConfigure;

        LoadingCache loadingCache = Cache.getKafkaConsumerCache(newConfigure);
        String[] consumerIds = newConfigure.getProperty("kafka.properties","bootstrap.servers").split(",");
        Arrays.sort(consumerIds);
        String consumerIdString = Arrays.toString(consumerIds);
        if(loadingCache.asMap().containsKey(consumerIdString)) {
            loadingCache.put(consumerIds, new ConsumerHandler(consumerIdString, KafkaFactory.getFactory(newConfigure).newConsumer()));
        }
    }

    public void updatePropertiesByPropertyName(String propertyName,Map<String, String> properties) throws IOException {
        String filePath = Configure.CONFIG_PATH + propertyName;
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
        Properties props = configure.getProperties(propertyName);
        for(String key : properties.keySet()) {
            props.setProperty(key.replaceAll("_","."), properties.get(key));
        }
        LOG.info("updateProperties new:" + props);
        props.store(bw, "update properties");
    }
}
