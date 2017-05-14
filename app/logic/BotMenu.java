package logic;

import helper.MessageButtonDecorator;
import logic.externalServices.TelegramApi;
import logic.recomendation.GenerateRecomendation;
import logic.recomendation.SetRecommendation;
import models.dbModels.WordModel;
import models.telegramModels.IncomingBotMessage;
import models.telegramModels.SendMessageModel;
import play.Configuration;
import play.libs.ws.WSClient;

import java.util.LinkedList;
import java.util.List;

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
        if (command.equals(JacobConst.TELEGRAM_COMMANDS.menu)) {
            sendOpenMenuMessage(message);
        } else if (command.equals(JacobConst.TELEGRAM_COMMANDS.getRecomendation)) {
            sendRecomendation(message);
        } else if (command.startsWith(JacobConst.TELEGRAM_COMMANDS.reomendPrefix)) {
            addWord(message);
        }
    }

    private void addWord(IncomingBotMessage message) {
        String recomend = message.message.text;
        String[] puts = recomend.split(":");
        if (recomend.length() < 3) {
            SendMessageModel messageModel = new SendMessageModel();
            messageModel.text = JacobConst.TELEGRAM_RESPONSE.recomendationFinish;
            messageModel.chat_id = message.message.chat.id;
            MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);
            decorator.addMenu();
            telegramApi.sendMessage(messageModel);
            return;
        } else {
            SetRecommendation setRecommendation = new SetRecommendation(String.valueOf(message.message.from.id));
            setRecommendation.setRecomendationList(puts[1]);
            sendRecomendation(message);
        }

    }

    private void sendRecomendation(IncomingBotMessage message) {
        List<WordModel> reommendation = new GenerateRecomendation(String.valueOf(message.message.from.id))
                .getRecomendationList();

        SendMessageModel messageModel = new SendMessageModel();
        messageModel.text = JacobConst.TELEGRAM_RESPONSE.recomendationListTitle;
        messageModel.chat_id = message.message.chat.id;
        MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);

        for (int i = 0; i < 5; i++) {
            List<String> buttonRow = new LinkedList<>();
            for (int j = 0; j < 5; j++) {
                if ((i == 4 && j == 4) || i * 5 + j >= reommendation.size()) {
                    buttonRow.add(JacobConst.TELEGRAM_COMMANDS.reomendPrefix + ":stop");
                    break;
                }
                WordModel currentRecommend = reommendation.get(5 * i + j);
                buttonRow.add(JacobConst.TELEGRAM_COMMANDS.reomendPrefix + ":" + currentRecommend.id + ":" + currentRecommend.wordEng);
            }
            decorator.addButtonsLine(buttonRow);
        }

        telegramApi.sendMessage(messageModel);

    }

    private void sendOpenMenuMessage(IncomingBotMessage message) {
        SendMessageModel messageModel = new SendMessageModel();
        messageModel.text = JacobConst.TELEGRAM_RESPONSE.menuMessage;
        messageModel.chat_id = message.message.chat.id;
        MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);
        decorator.addButtonsLine(JacobConst.TELEGRAM_COMMANDS.getRecomendation,
                JacobConst.TELEGRAM_COMMANDS.getSetForLearn);
        telegramApi.sendMessage(messageModel);
    }
}
