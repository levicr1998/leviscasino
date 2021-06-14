module Client {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.logging;
	requires gson;
	requires java.sql;
	requires java.ws.rs;
	requires org.junit.jupiter.api;
	requires javax.websocket.client.api;
	requires java.desktop;
	requires websocketshared;
	requires slf4j.api;
    requires org.apache.httpcomponents.httpclient;
	requires org.apache.httpcomponents.httpcore;

	opens org.cri.levi.websocketcasinoclient.frontend.controllers to javafx.fxml;
	exports org.cri.levi.websocketcasinoclient.frontend;
	exports org.cri.levi.websocketcasinoclient.frontend.controllers;
	exports org.cri.levi.websocketcasinoclient.endpoint.rest;
	exports org.cri.levi.websocketcasinoclient.endpoint.websocket;
	exports org.cri.levi.websocketcasinoclient.frontend.logic;
}