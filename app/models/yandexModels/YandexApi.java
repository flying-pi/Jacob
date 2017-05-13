package ExternalService;

import com.fasterxml.jackson.databind.JsonNode;
import models.requestModel.LanguageDetectModel;
import models.requestModel.TextTranslateModel;
import play.Configuration;
import play.Logger;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.annotation.processing.Completion;
import java.util.concurrent.CompletionStage;

/**
 * Created by sasha on 5/13/17.
 */
public class YandexApi {

    static String path;
    static String detect;
    static String translate;
    private final WSClient ws;
    public LanguageDetectModel languageDetectModel;
    public TextTranslateModel textTranslateModel;

    public YandexApi(WSClient ws, Configuration configuration) {
        this.ws = ws;
        path = configuration.getString("yandex.translateApi");
        detect = configuration.getString("yandex.checkLanguage");
        translate = configuration.getString("yandex.translateRequest");
    }

    //    public void languageDetect(String text){
    public void languageDetect(LanguageDetectModel languageDetectModel, String text){
        CompletionStage<WSResponse> response = ws.url(detect + path + "&" + text)
                .setContentType("application/x-www-form-urlencoded")
                .get();
        final CompletionStage<JsonNode> result = response.thenApply(wsResponse->{
            JsonNode json = wsResponse.asJson();
            return json;
        });
//        languageDetectModel = result;
    }

    //    public void translateWord(String translatedWord, String lang){
    public void translateWord(TextTranslateModel textTranslateModel, String text){
        if(textTranslateModel.lang == "ru") {
            textTranslateModel.lang = "ru-en";
        } else {
            textTranslateModel.lang = "en-ru";
        }
        CompletionStage<WSResponse> response = ws.url(translate + path + "&" + translatedWord
                +"&"+lang).setContentType("application/x-www-form-urlencoded").get();
        final CompletionStage<JsonNode> result = response.thenApply(wsResponse->{
            JsonNode json = wsResponse.asJson();
            return json;
        });
//        textTranslateModel = result;
    }
}
