package logic.textImport;

import logic.JacobConst;
import models.dbModels.LinkContentModels;
import models.telegramModels.IncomingBotMessage;
import play.Configuration;
import play.libs.ws.WSClient;

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
        LinkContentModels content = new LinkContentModels();
        content.content = "";
        String messageText = message.message.text;
        int from = message.message.entities.get(0).offset;
        int to = message.message.entities.get(0).length + from;
        content.url = messageText.substring(from, to);

        content.insert();
    }
}
