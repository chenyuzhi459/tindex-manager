package io.sugo.http.jersy;


import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;


import org.apache.http.params.CoreConnectionPNames;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by chenyuzhi on 17-11-4.
 */
public class JersyClientFactory {
	private static final int HTTP_MAX_CONNECTION = 200;
	private static final int DEFAULT_MAX_PERROUTE = 20;
	private static int CONNECT_TIMEOUT = 5000;
	private static int READ_TIMEOUT = 5000;
	private static int POOLCM_SOCKET_TIMEOUT = 3000;




	public static Client create(){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT,CONNECT_TIMEOUT);
		clientConfig.property(ClientProperties.READ_TIMEOUT,READ_TIMEOUT);

		PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager();
		pcm.setDefaultSocketConfig(
				SocketConfig.custom().
				setSoTimeout(POOLCM_SOCKET_TIMEOUT).
				build());    //TO Test
		pcm.setMaxTotal(HTTP_MAX_CONNECTION);
		pcm.setDefaultMaxPerRoute(DEFAULT_MAX_PERROUTE);

		clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, pcm);

		clientConfig.connectorProvider(new ApacheConnectorProvider());
//		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);
		client.register(JacksonFeature.class);
		return client;

	}


}
