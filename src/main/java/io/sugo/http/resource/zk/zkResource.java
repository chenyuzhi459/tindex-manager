package io.sugo.http.resource.zk;

import com.google.common.collect.ImmutableMap;
import io.sugo.http.resource.Resource;
import io.sugo.zookeeper.ClientHandler;
import io.sugo.zookeeper.client.CuratorZookeeperClient;
import io.sugo.zookeeper.factory.ZkFactory;
import org.apache.zookeeper.CreateMode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenyuzhi on 17-10-24.
 */
@Path("/zk")
public class zkResource extends Resource {

	private static  final String _EPHEMERAL_SEQUENTIAL = "EPHEMERAL_SEQUENTIAL";
	private static  final String _PERSISTENT_SEQUENTIAL = "PERSISTENT_SEQUENTIAL";
	private static  final String _EPHEMERAL = "EPHEMERAL";
	private static  final String _PERSISTENT = "PERSISTENT";
	private final CuratorZookeeperClient zkClient;

	public zkResource() throws ExecutionException {
		this.zkClient = ZkFactory.getFactory(configure).getClientHandler().getClient();
//				new CuratorZookeeperClient(configure);
	}

	public void close(){
		zkClient.close();
	}

	@GET
	@Path("/data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData(
			@QueryParam("path") String  path
	)
	{
		return Response.ok(ImmutableMap.of("sourceData",zkClient.getData(path))).build();
	}

	@GET
	@Path("/children")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildren(
			@QueryParam("path") String  path
	)
	{
		List<String> children = zkClient.getChildren(path);
		if(null == children){
			return Response.noContent().build();
		}
		Collections.sort(children);
		return Response.ok(children).build();
	}

	@POST
	@Path("/data")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setData(
			@QueryParam("path") String path,
			String data
	)
	{
		if(!zkClient.setData(path,data)){
			return Response.noContent().build();
		}
		return Response.ok().build();
	}

	@POST
	@Path("/node")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNode(
			@QueryParam("path") String path,
			@QueryParam("mode") String mode,
			String data
	)
	{
		CreateMode createMode;
		switch (mode){
			case _EPHEMERAL:
				createMode = CreateMode.EPHEMERAL;
				break;
			case _PERSISTENT:
				createMode = CreateMode.PERSISTENT;
				break;
			case _EPHEMERAL_SEQUENTIAL:
				createMode = CreateMode.EPHEMERAL_SEQUENTIAL;
				break;
			case _PERSISTENT_SEQUENTIAL:
				createMode = CreateMode.PERSISTENT_SEQUENTIAL;
				break;
			default:
				createMode = CreateMode.PERSISTENT;
				break;
		}
		if(null == zkClient.create(path,createMode,data)){
			return Response.noContent().build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("/node")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNode(
			@QueryParam("path") String path
	){
		if(!zkClient.delete(path)){
			return Response.noContent().build();
		}

		return Response.ok().build();
	}

}
