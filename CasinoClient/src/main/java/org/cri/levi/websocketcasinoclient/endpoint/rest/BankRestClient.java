package org.cri.levi.websocketcasinoclient.endpoint.rest;

import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.cri.levi.websocketcasinoshared.models.bankmodels.TransferModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.LoggerFactory;

public class BankRestClient {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BankRestClient.class);
    private static final String URL = "http://localhost:8105/bank/";
    private final Gson gson = new Gson();


    public Player deposit(TransferModel transferModel) {
        final String query = URL + "deposit";
        log.info("POST: {}", query);

        HttpPost httpPostQuery = QueryCommandExecutor.getInstance().makePostQuery(query, transferModel);

        return QueryCommandExecutor.getInstance().executeQuery(httpPostQuery);
    }

    public Player withdraw(TransferModel transferModel) {
        final String query = URL + "withdraw";
        log.info("POST: {}", query);

        HttpPost httpPostQuery = QueryCommandExecutor.getInstance().makePostQuery(query, transferModel);

        return QueryCommandExecutor.getInstance().executeQuery(httpPostQuery);
    }

}
