package org.cri.levi.websocketcasinoclient.endpoint.rest;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cri.levi.websocketcasinoshared.models.Player;
import org.slf4j.LoggerFactory;

public class QueryCommandExecutor {
    private final Gson gson = new Gson();
    private static QueryCommandExecutor instance;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(QueryCommandExecutor.class);

    public static QueryCommandExecutor getInstance() {
        if (instance == null) {
            instance = new QueryCommandExecutor();
        }
        return instance;
    }

    public HttpPost makePostQuery(String query, Object model) {
        HttpPost httpPostQuery = new HttpPost(query);
        httpPostQuery.addHeader("Content-Type", "application/json");
        StringEntity params;

        try {
            String jsonString = gson.toJson(model);
            params = new StringEntity(jsonString);
            httpPostQuery.setEntity(params);
            return httpPostQuery;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return httpPostQuery;
    }

    public Player executeQuery(HttpRequestBase requestBaseQuery) {
        Player player = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(requestBaseQuery)) {
            log.info("Status: {}", response.getStatusLine());

            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            log.info("JSON entity: {}", entityString);

            player = gson.fromJson(entityString, Player.class);

        } catch (Exception e) {
            log.error(e.toString());
        }

        return player;
    }
}
