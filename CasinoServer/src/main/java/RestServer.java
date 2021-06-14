import org.cri.levi.websocketcasinoserver.endpoint.BankRestEndpoint;
import org.cri.levi.websocketcasinoserver.endpoint.LoginRestEndpoint;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestServer {

    private static final Logger log = LoggerFactory.getLogger(RestServer.class);

    public static void main(String[] args) {
        try {
            startBankServer();
            startLoginServer();


        } catch (Exception e) {
            log.info(e.getMessage());
        }


    }

    public static void startLoginServer() throws Exception {
        Server loginServer = new Server(8104);
        loginServer.setHandler(getJerseyHandlerLogin());
        loginServer.start();
        log.info("LoginServer started");
        log.info("LoginServer joined");
    }

    public static void startBankServer() throws Exception {
       Server bankServer = new Server(8105);
        bankServer.setHandler(getJerseyHandlerBank());
        bankServer.start();
        log.info("BankServer started");
        log.info("BankServer joined");
    }

    private static Handler getJerseyHandlerLogin() {
        log.info("Create Jersey handler");
        ServletContextHandler loginHandler = new ServletContextHandler(
                ServletContextHandler.SESSIONS);

        loginHandler.setContextPath("/account");

        ServletHolder loginServletHolder = loginHandler.addServlet(ServletContainer.class, "/*");
        loginServletHolder.setInitOrder(0);
        loginServletHolder.setInitParameter("jersey.config.server.provider.classnames",
                LoginRestEndpoint.class.getCanonicalName());

        return loginHandler;
    }

    private static Handler getJerseyHandlerBank() {
        log.info("Create Jersey handler");
        ServletContextHandler handler = new ServletContextHandler(
                ServletContextHandler.SESSIONS);

        handler.setContextPath("/bank");

        ServletHolder servletHolder = handler.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                BankRestEndpoint.class.getCanonicalName());

        return handler;
    }
}