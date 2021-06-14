package org.cri.levi.websocketcasinoclient.endpoint.rest;

import org.apache.http.client.methods.HttpPost;
import org.cri.levi.websocketcasinoshared.models.loginmodels.LoginModel;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.LoggerFactory;

public class LoginRestClient {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginRestClient.class);
    private static final String URL = "http://localhost:8104/account/";


    public Player login(LoginModel loginModel) {
        final String query = URL + "login";
        log.info("POST: {}", query);

        HttpPost httpPostQuery = QueryCommandExecutor.getInstance().makePostQuery(query, loginModel);

        return QueryCommandExecutor.getInstance().executeQuery(httpPostQuery);
    }

    public Player register(LoginModel loginModel) {
        final String query = URL + "register";
        log.info("POST: {}",query);

        HttpPost httpPostQuery = QueryCommandExecutor.getInstance().makePostQuery(query, loginModel);


        return QueryCommandExecutor.getInstance().executeQuery(httpPostQuery);
    }

}
