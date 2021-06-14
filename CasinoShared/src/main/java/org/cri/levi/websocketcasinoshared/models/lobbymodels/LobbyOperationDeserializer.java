package org.cri.levi.websocketcasinoshared.models.lobbymodels;

import com.google.gson.*;

import java.lang.reflect.Type;

public class LobbyOperationDeserializer implements JsonDeserializer<LobbyOperation> {
    private Gson gson = new Gson();
    private String actionString = "action";
    private String optionString = "option";

    @Override
    public LobbyOperation deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();


        LobbyOperationOption option = null;
        LobbyActionEnum action = null;

        if (jsonObject.get(actionString).getAsString().equals("CREATEGAME")) {
            action = LobbyActionEnum.CREATEGAME;
            option = gson.fromJson(jsonObject.get(optionString), CreateGameModel.class);

        } else if (jsonObject.get(actionString).getAsString().equals("NEWLOBBY")) {
            action = LobbyActionEnum.NEWLOBBY;
            option = gson.fromJson(jsonObject.get(optionString), NewGameModel.class);
        } else if (jsonObject.get(actionString).getAsString().equals("LOADGAMES")) {
            action = LobbyActionEnum.LOADGAMES;
            option = null;
        } else if (jsonObject.get(actionString).getAsString().equals("LOADGAMESCLIENT")) {
            action = LobbyActionEnum.LOADGAMESCLIENT;
            option = gson.fromJson(jsonObject.get(optionString), GetGamesModel.class);
        } else if (jsonObject.get(actionString).getAsString().equals("GENERATENUMBER")) {
            action = LobbyActionEnum.GENERATENUMBER;
            option = gson.fromJson(jsonObject.get(optionString), NumberGeneratorModel.class);
        }
        return new LobbyOperation(action, option);
    }
}
