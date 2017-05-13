package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.textImport.TextLoader;
import models.telegramModels.IncomingBotMessage;
import play.Configuration;
import play.Logger;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class BotController extends Controller {

    private final WSClient wsClient;
    private final Configuration configuration;

    private List<IsCanProccess> proccessCheck = new ArrayList<>();
    private List<MessageProcessor> processors = new ArrayList<>();

    private final ObjectMapper mapper;

    private final TextLoader textLoader;

    @Inject
    public BotController(WSClient wsClient, Configuration configuration) {
        this.wsClient = wsClient;
        this.configuration = configuration;

        textLoader = new TextLoader(wsClient, configuration);

        this.mapper = new ObjectMapper();

        proccessCheck.add(TextLoader::isContainUrl);
        processors.add(textLoader::proccessMessage);

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

        for (int i = 0; i < proccessCheck.size(); i++) {
            if (proccessCheck.get(i).isCanProccess(message)) {
                processors.get(i).proccess(message);
                break;
            }
        }

        return ok("Jacob is Cool");
    }

    public interface IsCanProccess {
        public boolean isCanProccess(IncomingBotMessage message);
    }

    public interface MessageProcessor {
        void proccess(IncomingBotMessage message);
    }
}
