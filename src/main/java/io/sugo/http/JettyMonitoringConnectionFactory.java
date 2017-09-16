package io.sugo.http;

import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.util.component.ContainerLifeCycle;

import java.util.concurrent.atomic.AtomicInteger;

public class JettyMonitoringConnectionFactory extends ContainerLifeCycle implements ConnectionFactory
{
  private final ConnectionFactory connectionFactory;
  private final AtomicInteger activeConns;

  public JettyMonitoringConnectionFactory(ConnectionFactory connectionFactory, AtomicInteger activeConns)
  {
    this.connectionFactory = connectionFactory;
    addBean(connectionFactory);
    this.activeConns = activeConns;
  }

  @Override
  public String getProtocol()
  {
    return connectionFactory.getProtocol();
  }

  @Override
  public Connection newConnection(Connector connector, EndPoint endPoint)
  {
    final Connection connection = connectionFactory.newConnection(connector, endPoint);
    connection.addListener(
        new Connection.Listener()
        {
          @Override
          public void onOpened(Connection connection)
          {
            activeConns.incrementAndGet();
          }

          @Override
          public void onClosed(Connection connection)
          {
            activeConns.decrementAndGet();
          }
        }
    );
    return connection;
  }
}
