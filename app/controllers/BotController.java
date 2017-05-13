package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.telegramModels.IncomingBotMessage;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class BotController extends Controller {

    private final ObjectMapper mapper;

    public BotController() {
        this.mapper = new ObjectMapper();

    }

    public Result receiveCommonBotMessage() {
        JsonNode requestData = request().body().asJson();
        if (requestData == null) {
            Logger.error("can not get json from telegram request");
            return ok();
        }
        Logger.info("getting new telegram bot message :: " + requestData);

        IncomingBotMessage message;
        try {
            message = mapper.treeToValue(requestData, IncomingBotMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.error("can not  parse json", e);
            return ok();
        }
        Logger.info("message from telegram :: " + message);

        return ok("Jacob is Cool");
    }
}
