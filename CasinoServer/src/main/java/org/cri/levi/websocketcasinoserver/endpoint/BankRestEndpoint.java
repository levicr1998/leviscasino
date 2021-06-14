package org.cri.levi.websocketcasinoserver.endpoint;


import com.google.gson.Gson;
import org.cri.levi.websocketcasinoserver.database.bank.Bank;
import org.cri.levi.websocketcasinoshared.models.bankmodels.TransferModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
public class BankRestEndpoint {
    private static final Logger log = LoggerFactory.getLogger(BankRestEndpoint.class);
    private static Bank bank = Bank.getInstance();
    private final Gson gson;


    public BankRestEndpoint() {
        gson = new Gson();
    }

    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response deposit(TransferModel transferModel) {
        log.info("POST deposit: {}", transferModel);
        Player myResponse = null;
        try {
            myResponse = bank.deposit(transferModel);
        } catch (SQLException e) {
          log.info(e.getMessage());
        }
        return Response.status(200).entity(gson.toJson(myResponse)).build();
    }

    @Path("/withdraw")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response withdraw(TransferModel transferModel) {
        log.info("POST withdraw: {}", transferModel);
        Player myResponse = null;
        try {
            myResponse = bank.withdraw(transferModel);
        } catch (SQLException e) {
       log.info(e.getMessage());
        }
        return Response.status(200).entity(gson.toJson(myResponse)).build();
    }
}
