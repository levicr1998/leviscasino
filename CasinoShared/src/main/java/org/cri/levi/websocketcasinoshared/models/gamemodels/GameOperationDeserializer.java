package org.cri.levi.websocketcasinoshared.models.gamemodels;


import com.google.gson.*;

import java.lang.reflect.Type;

public class GameOperationDeserializer implements JsonDeserializer<GameOperation> {
    private Gson gson = new Gson();
    private String actionString = "action";
    private String optionString = "option";

    @Override
    public GameOperation deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();


        GameOperationOption option = null;
        CasinoActionEnum action = null;

        if (jsonObject.get(actionString).getAsString().equals("PLACEBET")) {
            action = CasinoActionEnum.PLACEBET;
            option = gson.fromJson(jsonObject.get(optionString), PlaceBetModel.class);

        } else if (jsonObject.get(actionString).getAsString().equals("UPDATEPLAYERS")) {
            action = CasinoActionEnum.UPDATEPLAYERS;
            option = gson.fromJson(jsonObject.get(optionString), UpdatePlayersModel.class);
        } else if (jsonObject.get(actionString).getAsString().equals("SENDPROFIT")) {
            action = CasinoActionEnum.SENDPROFIT;
            option = gson.fromJson(jsonObject.get(optionString), ProfitModel.class);
        } else if (jsonObject.get(actionString).getAsString().equals("LEAVEGAME")) {
            action = CasinoActionEnum.LEAVEGAME;
            option = gson.fromJson(jsonObject.get(optionString), LeaveGameModel.class);
        }
       return new GameOperation(action, option);
    }
}
