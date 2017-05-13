package logic.externalServices;

import com.fasterxml.jackson.databind.JsonNode;
import models.telegramModels.SendMessageModel;
import play.Configuration;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class TelegramApi {
    static String path;
    private final WSClient ws;

        public TelegramApi(WSClient ws, Configuration configuration) {
        this.ws = ws;
        path = configuration.getString("telegram.botApi");
    }
    public  void sendMessage(SendMessageModel message) {
        CompletionStage<WSResponse> response = ws.url(path + "sendMessage")
                .setContentType("application/json")
                .post(Json.toJson(message));
        final CompletionStage<JsonNode> result= response.thenApply(wsResponse -> {
            JsonNode json = wsResponse.asJson();
            return json;
        });
    }

}
