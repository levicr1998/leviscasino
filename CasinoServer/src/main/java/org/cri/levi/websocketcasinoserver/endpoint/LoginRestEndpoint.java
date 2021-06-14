package org.cri.levi.websocketcasinoserver.endpoint;

import com.google.gson.Gson;
import org.apache.http.auth.InvalidCredentialsException;
import org.cri.levi.websocketcasinoserver.database.authenticator.Authenticator;
import org.cri.levi.websocketcasinoshared.models.loginmodels.LoginModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
public class LoginRestEndpoint {

    private static final Logger log = LoggerFactory.getLogger(LoginRestEndpoint.class);
    private static Authenticator authenticator = Authenticator.getInstance();
    private final Gson gson;

    public LoginRestEndpoint() {
        gson = new Gson();
    }

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response login(LoginModel loginModel) {
        log.info("POST login: {}", loginModel);
        Player myResponse = null;
        try {
            myResponse = authenticator.login(loginModel.getUsername(), loginModel.getPassword());
        } catch (SQLException | InvalidCredentialsException e) {
            log.info(e.getMessage());
        }

        return Response.status(200).entity(gson.toJson(myResponse)).build();
    }

    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response register(LoginModel loginModel) {
        log.info("POST register: {}", loginModel.getUsername());

        try {
            Player player = authenticator.register(loginModel);
            return Response.status(200).entity(gson.toJson(player)).build();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}