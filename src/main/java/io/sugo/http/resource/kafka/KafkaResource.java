package io.sugo.http.resource.kafka;

import io.sugo.http.resource.Resource;
import io.sugo.kafka.KafkaHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenyuzhi on 17-10-21.
 */
@Path("/kafka")
public class KafkaResource extends Resource {
	private final KafkaHandler kafkaHandler;

	public KafkaResource() throws ExecutionException {
		this.kafkaHandler = new KafkaHandler(configure);
	}

	@POST
	@Path("/{topic}/offsets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopicOffsets(
			@PathParam("topic") String topic,
			final List partitionIds
	)
	{
		return Response.ok(kafkaHandler.getTopicPartitionOffset(topic,partitionIds)).build();
	}
}