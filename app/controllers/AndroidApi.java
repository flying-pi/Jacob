package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.learn.WordSetGenerator;
import logic.recomendation.GenerateRecomendation;
import logic.recomendation.SetRecommendation;
import logic.textImport.TextLoader;
import models.android.*;
import models.dbModels.WordModel;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class AndroidApi extends Controller {

    private final WSClient wsClient;
    private final Configuration configuration;

    private final ObjectMapper mapper;

    private final TextLoader textLoader;

    @Inject
    public AndroidApi(WSClient wsClient, Configuration configuration) {
        this.wsClient = wsClient;
        this.configuration = configuration;

        textLoader = new TextLoader(wsClient, configuration);

        this.mapper = new ObjectMapper();

    }


    public Result addText() {
        JsonNode requestData = request().body().asJson();
        if (requestData == null) {
            Logger.error("can not get json from android request");
            return ok();
        }
        Logger.info("getting new android message :: " + requestData);

        InsertTextRequestModel message;
        try {
            message = mapper.treeToValue(requestData, InsertTextRequestModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.error("can not  parse json", e);
            return ok();
        }
        Logger.info("message from android :: " + message);
        textLoader.loadText(message);
        return ok(Json.toJson(new AndroidOkResponse()));
    }

    public Result getRecomendation() {
        JsonNode requestData = request().body().asJson();
        if (requestData == null) {
            Logger.error("can not get json from android request");
            return ok();
        }
        Logger.info("getting new android message :: " + requestData);

        RequestWithUserID message;
        try {
            message = mapper.treeToValue(requestData, RequestWithUserID.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.error("can not  parse json", e);
            return ok();
        }
        Logger.info("message from android :: " + message);
        GenerateRecomendation generateRecomendation = new GenerateRecomendation(message.userID);
        List<RecommendationWord> result = new LinkedList<>();
        generateRecomendation.getRecomendationList().forEach(wordModel -> result.add(new RecommendationWord(wordModel)));
        return ok(Json.toJson(result));
    }

    public Result setRecomendation() {
        JsonNode requestData = request().body().asJson();
        if (requestData == null) {
            Logger.error("can not get json from android request");
            return ok();
        }
        Logger.info("getting new android message :: " + requestData);

        SetRecommendationRequest message;
        try {
            message = mapper.treeToValue(requestData, SetRecommendationRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.error("can not  parse json", e);
            return ok();
        }
        Logger.info("message from android :: " + message);
        SetRecommendation setRecommendation = new SetRecommendation(message.userID);
        setRecommendation.setRecomendationList(message.wordIds);
        return ok(Json.toJson(new AndroidOkResponse()));
    }

    public Result getAllWordSet() {
        JsonNode requestData = request().body().asJson();
        if (requestData == null) {
            Logger.error("can not get json from android request");
            return ok();
        }
        Logger.info("getting new android message :: " + requestData);

        RequestWithUserID message;
        try {
            message = mapper.treeToValue(requestData, RequestWithUserID.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.error("can not  parse json", e);
            return ok();
        }
        Logger.info("message from android :: " + message);

        WordSetGenerator wordSetGenerator = new WordSetGenerator(message.userID);
        List<WordModel> words = wordSetGenerator.getWordSetForUser();
        List<RecommendationWord> listForSend = new ArrayList<>();
        words.forEach(wordModel -> listForSend.add(new RecommendationWord(wordModel)));
        return ok(Json.toJson(listForSend));

    }
}
