package io.sugo.http.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;

/**
 * Created by chenyuzhi on 17-10-9.
 */
@Path("/druid/worker/v1/task")
public class ChatHandlerResource extends ForwardResource {
	public ChatHandlerResource(){
		pathPre = "http://{0}/druid/worker/v1";
	}

	@GET
	@Path("/chat/{id}/period")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatus(
			@PathParam("id") String handlerId,
			@QueryParam("location") String location
	){
		String url = MessageFormat.format("{0}/chat/{1}/status",MessageFormat.format(pathPre,location),handlerId);
		return httpMethod.get(url);
	}

	@GET
	@Path("/chat/{id}/offsets/current")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOffset(
			@PathParam("id") String handlerId,
			@QueryParam("location") String location
	){
		String url = MessageFormat.format("{0}/chat/{1}/offsets/current",MessageFormat.format(pathPre,location),handlerId);
		return httpMethod.get(url);
	}

	@GET
	@Path("/chat/{id}/metrics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMetrics(
			@PathParam("id") String handlerId,
			@QueryParam("location") String location
	)
	{
		String url = MessageFormat.format("{0}/chat/{1}/metrics",MessageFormat.format(pathPre,location),handlerId);
		System.out.println("url:" + url);
		return httpMethod.get(url);
	}

	@GET
	@Path("/chat/{id}/summary")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSummaryInof(
			@PathParam("id") String handlerId,
			@QueryParam("location") String location
	)
	{
		String url = MessageFormat.format("{0}/chat/{1}/summary",MessageFormat.format(pathPre,location),handlerId);
		System.out.println("url:" + url);
		return httpMethod.get(url);
	}
}
