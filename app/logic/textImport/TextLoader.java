package logic.textImport;

import logic.JacobConst;
import logic.externalServices.TelegramApi;
import models.android.InsertTextRequestModel;
import models.dbModels.UserModel;
import models.telegramModels.IncomingBotMessage;
import models.telegramModels.SendMessageModel;
import play.Configuration;
import play.Logger;
import play.libs.ws.WSClient;

import static logic.Utils.clear;
import static logic.Utils.getTextByUrl;
import static models.dbModels.UserModel.getUserByChatMessage;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class TextLoader {
    TelegramApi telegramApi;

    public static boolean isContainUrl(IncomingBotMessage message) {
        try {
            return message.message.entities.get(0).type.equals(JacobConst.ENTITIES_TYPE.URL);
        } catch (Exception e) {
            return false;
        }
    }

    private final WSClient ws;
    private final Configuration configuration;

    public TextLoader(WSClient ws, Configuration configuration) {
        this.ws = ws;
        this.configuration = configuration;
        telegramApi = new TelegramApi(ws, configuration);
    }

    public void proccessMessage(IncomingBotMessage message) {
        new ContentLoder(message).start();
        SendMessageModel response = new SendMessageModel();
        response.text = JacobConst.TELEGRAM_RESPONSE.loadedData;
        response.chat_id = message.message.chat.id;
        telegramApi.sendMessage(response);
    }

    public void loadText(InsertTextRequestModel message) {
        new ContentLoder(message).start();
    }


    private class ContentLoder extends Thread {

        private InsertTextRequestModel restRequest;
        private IncomingBotMessage telegramMessage;
        private String content;
        private final String url;

        public ContentLoder(IncomingBotMessage message) {
            this.telegramMessage = message;

            int from = message.message.entities.get(0).offset;
            int to = message.message.entities.get(0).length + from;

            this.url = message.message.text.substring(from, to);
            this.content = "";
        }

        public ContentLoder(InsertTextRequestModel message) {
            url = null;
            this.restRequest = message;
        }

        @Override
        public void run() {
            super.run();
            UserModel user;
            if (telegramMessage != null) {
                user = getUserByChatMessage(telegramMessage);
                this.content = clear(getTextByUrl(url));
            } else {
                user = getUserByChatMessage(restRequest);
                this.content = restRequest.text;
            }
            Logger.info("starting processing word");
            ITextAnalayzer analyzer = new TextLoaderFirstVariant();
            analyzer.setResultListener(wordFrequencyMap -> {
                Logger.info("getting new words set for saving ");
                wordFrequencyMap.forEach(user::addNewEnglisWord);
            });
            analyzer.analyze(content);
        }
    }
}
