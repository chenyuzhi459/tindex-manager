package io.sugo.http.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by chenyuzhi on 17-10-9.
 */
@Path("/druid/worker/v1/task")
public class ChatHandlerResource extends Resource {
	public ChatHandlerResource(){
		pathPre = "http://{0}/druid/worker/v1";
	}

	@GET
	@Path("/chat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOffset(
			@PathParam("id") String handlerId,
			@QueryParam("location") String location
	){
		return null;
	}

}
