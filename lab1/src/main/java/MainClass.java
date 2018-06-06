import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


public class MainClass {

    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[] { "./web/index.html" });
        resource_handler.setResourceBase("./src/main/resources/");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/connect");

        ServletHolder servletHolder = new ServletHolder("ws-events", new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(MainWebSocket.class);
            }
        });
        context.addServlet(servletHolder, "/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {context, resource_handler});
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.getStackTrace();
        }
    }
}