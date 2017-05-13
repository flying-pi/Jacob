package logic;

import helper.MessageButtonDecorator;
import logic.externalServices.TelegramApi;
import models.telegramModels.IncomingBotMessage;
import models.telegramModels.SendMessageModel;
import play.Configuration;
import play.libs.ws.WSClient;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class BotMenu {
    TelegramApi telegramApi;

    public static boolean IsMenu(IncomingBotMessage message) {
        try {
            return message.message.entities.get(0).type.equals(JacobConst.ENTITIES_TYPE.BOT_COMMAND);
        } catch (Exception e) {
            return false;
        }
    }

    private final WSClient ws;
    private final Configuration configuration;

    public BotMenu(WSClient ws, Configuration configuration) {
        this.ws = ws;
        this.configuration = configuration;
        telegramApi = new TelegramApi(ws, configuration);
    }

    public void proccessMessage(IncomingBotMessage message) {
        String command = message.message.text;
        if (command.endsWith(JacobConst.TELEGRAM_COMMANDS.menu)) {
            SendMessageModel messageModel = new SendMessageModel();
            messageModel.text = JacobConst.TELEGRAM_RESPONSE.menuMessage;
            messageModel.chat_id = message.message.chat.id;
            MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);
            decorator.addButtonsLine(JacobConst.TELEGRAM_COMMANDS.getRecomendation,
                    JacobConst.TELEGRAM_COMMANDS.getSetForLearn);
            telegramApi.sendMessage(messageModel);

        }
    }
}
