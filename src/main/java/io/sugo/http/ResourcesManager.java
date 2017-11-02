package io.sugo.http;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import io.sugo.http.filter.CrossDomainSupportFilter;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ResourcesManager {

    private static final Logger LOG = Logger.getLogger(ResourcesManager.class);
    public static int port;
    private static boolean developMode;

    private static final AtomicInteger activeConnections = new AtomicInteger();
    public static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        Configure configure = Configure.getConfigure();
        port = configure.getInt("system.properties","http.port");
        developMode = configure.getBoolean("system.properties","develop.mode");

        Server server = null;
        try {
            server = makeJettyServer();
            initialize(server);
            server.start();
            LOG.info("start...in " + port);
            latch.await();
        } finally {
            if (server != null) {
                server.stop();
            }
            LOG.info("server stopped!");
        }

    }


    static Server makeJettyServer() {
        final Server server = new Server();

        // Without this bean set, the default ScheduledExecutorScheduler runs as non-daemon, causing lifecycle hooks to fail
        // to fire on main exit. Related bug: https://github.com/druid-io/druid/pull/1627
        server.addBean(new ScheduledExecutorScheduler("JettyScheduler", true), true);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setIdleTimeout(600000);
        // workaround suggested in -
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=435322#c66 for jetty half open connection issues during failovers
        connector.setAcceptorPriorityDelta(-1);

        List<ConnectionFactory> monitoredConnFactories = new ArrayList<>();
        for (ConnectionFactory cf : connector.getConnectionFactories()) {
            monitoredConnFactories.add(new JettyMonitoringConnectionFactory(cf, activeConnections));
        }
        connector.setConnectionFactories(monitoredConnFactories);

        server.setConnectors(new Connector[]{connector});

        return server;
    }

    static void initialize(Server server) {
        final ServletContextHandler htmlHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        htmlHandler.setContextPath("/");

        ServletHolder htmlHolder = new ServletHolder("default", DefaultServlet.class);
        htmlHolder.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        htmlHolder.setInitParameter("org.eclipse.jetty.servlet.Default.redirectWelcome", "true");

        htmlHandler.setWelcomeFiles(new String[]{"index.html"});
        htmlHandler.addServlet(htmlHolder, "/*");






        htmlHandler.setBaseResource(
                new ResourceCollection(
                        new String[]{
                                ResourcesManager.class.getClassLoader().getResource("dist").toExternalForm()
                        }
                )
        );


        final ServletContextHandler apiHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        apiHandler.setContextPath("/api");

        ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
        servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "io.sugo.http.resource");
        apiHandler.addServlet(servletHolder, "/*");
        if(developMode){
            apiHandler.addFilter(CrossDomainSupportFilter.class,"/*", EnumSet.of(DispatcherType.REQUEST));
        }


        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(apiHandler);
        handlerList.addHandler(htmlHandler);

        server.setHandler(handlerList);

    }

}
