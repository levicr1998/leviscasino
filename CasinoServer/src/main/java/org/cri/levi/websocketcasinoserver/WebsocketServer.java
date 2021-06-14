package org.cri.levi.websocketcasinoserver;

import org.cri.levi.websocketcasinoserver.endpoint.CasinoWebsocketService;
import org.cri.levi.websocketcasinoserver.endpoint.LobbyWebsocketService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.server.ServerContainer;

public class WebsocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebsocketServer.class);
    private static final int PORT_GAME = 8095;
    private static final int PORT_LOBBY = 8096;

    public static void main(String[] args) {
        Server gamewebSocketServer = new Server();
        startWebSocketServer(gamewebSocketServer, PORT_GAME, CasinoWebsocketService.class);
        Server lobbywebSocketServer = new Server();
        startWebSocketServer(lobbywebSocketServer, PORT_LOBBY, LobbyWebsocketService.class);
    }

    private static void startWebSocketServer(Server webSocketServer, int port, Class className) {

        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(port);
        webSocketServer.addConnector(connector);
        log.info("Connector added");

        ServletContextHandler webSocketContext = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);
        log.info("Context handler set");

        try {
            ServerContainer serverContainer = WebSocketServerContainerInitializer
                    .configureContext(webSocketContext);
            log.info("Initialize javax.websocket layer");

            serverContainer.addEndpoint(className);
            log.info("Endpoint added");

            webSocketServer.start();
            log.info("Server started");

        } catch (Exception e) {
            log.error("Error startWebSocketServer {0}", e);
        }
    }
}
