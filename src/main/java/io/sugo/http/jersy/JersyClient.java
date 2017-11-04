package io.sugo.http.jersy;


import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by chenyuzhi on 17-11-4.
 */
public class JersyClient  {
	private static  int HTTP_MAX_CONNECTION = 200;
	private static  int DEFAULT_MAX_PERROUTE = 20;
	private static int TIME_OUT = 5000;
	private static Client client = ClientBuilder.newClient();
	private static ClientConfig clientConfig;

	private static Client getInstance(){
		client = ClientBuilder.newClient();

		//clientConfig = new DefaultClientConfig();
		return null;
	}


}
