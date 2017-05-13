package logic.textImport;

import logic.JacobConst;
import models.dbModels.UserModel;
import models.telegramModels.IncomingBotMessage;
import play.Configuration;
import play.libs.ws.WSClient;

import static logic.Utils.clear;
import static logic.Utils.getTextByUrl;
import static models.dbModels.UserModel.getUserByChatMessage;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class TextLoader {

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
    }

    public void proccessMessage(IncomingBotMessage message) {
        new ContentLoder(message).start();
    }


    private class ContentLoder extends Thread {

        private final IncomingBotMessage message;
        private String content;
        private final String url;

        public ContentLoder(IncomingBotMessage message) {
            this.message = message;

            int from = message.message.entities.get(0).offset;
            int to = message.message.entities.get(0).length + from;

            this.url = message.message.text.substring(from, to);
            this.content = "";
        }

        @Override
        public void run() {
            super.run();
            this.content = clear(getTextByUrl(url));
            UserModel user = getUserByChatMessage(message);
            ITextAnalayzer analyzer = new TextLoaderFirstVariant();
            analyzer.setResultListener(wordFrequencyMap ->
                    wordFrequencyMap.forEach(user::addNewEnglisWord));
            analyzer.analyze(content);
        }
    }
}
