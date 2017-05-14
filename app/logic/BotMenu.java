package logic;

import helper.MessageButtonDecorator;
import logic.externalServices.TelegramApi;
import logic.learn.WordCheck;
import logic.learn.WordSetGenerator;
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
        } else if (command.equals(JacobConst.TELEGRAM_COMMANDS.getSetForLearn)) {
            displayLearnSet(message);
        } else if (command.equals(JacobConst.TELEGRAM_COMMANDS.sendMewWord)) {
            sendWord(message);
        }
    }

    private void sendWord(IncomingBotMessage message) {
        WordCheck check = new WordCheck(String.valueOf(message.message.from.id));
        WordModel word = check.getWord();
        if (word == null) {
            SendMessageModel messageModel = new SendMessageModel();
            messageModel.text = JacobConst.TELEGRAM_RESPONSE.emptySet;
            messageModel.chat_id = message.message.chat.id;
            MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);
            decorator.addMenu();
            telegramApi.sendMessage(messageModel);
            return;
        } else {
            SendMessageModel messageModel = new SendMessageModel();
            messageModel.text = JacobConst.TELEGRAM_RESPONSE.enterTranslate + word.wordRus
                    + " " + String.valueOf((int) (word.progress * 1000f));
            messageModel.chat_id = message.message.chat.id;
            MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);
            decorator.addMenu();
            telegramApi.sendMessage(messageModel);
            return;
        }

    }

    private void displayLearnSet(IncomingBotMessage message) {
        List<WordModel> wordSet = new WordSetGenerator(String.valueOf(message.message.from.id))
                .getWordSetForUser();

        SendMessageModel messageModel = new SendMessageModel();
        messageModel.text = JacobConst.TELEGRAM_RESPONSE.allWordSetTitle;
        messageModel.chat_id = message.message.chat.id;
        MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);


        wordSet.forEach(wordModel -> {
            List<String> buttons = new LinkedList<>();
            buttons.add(wordModel.wordEng + " => " + wordModel.wordRus);
            decorator.addButtonsLine(buttons);
        });

        decorator.addMenu();

        telegramApi.sendMessage(messageModel);
    }

    private void addWord(IncomingBotMessage message) {
        String recomend = message.message.text;
        String[] puts = recomend.split(":");
        if (puts.length < 3) {
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

        reommendation.forEach(wordModel -> {
            List<String> buttonRow = new LinkedList<>();
            buttonRow.add(JacobConst.TELEGRAM_COMMANDS.reomendPrefix + ":" + wordModel.id + ":" + wordModel.wordEng);
            decorator.addButtonsLine(buttonRow);
        });

        List<String> buttonRow = new LinkedList<>();
        buttonRow.add(JacobConst.TELEGRAM_COMMANDS.reomendPrefix + ":stop");
        decorator.addButtonsLine(buttonRow);

        telegramApi.sendMessage(messageModel);

    }

    private void sendOpenMenuMessage(IncomingBotMessage message) {
        SendMessageModel messageModel = new SendMessageModel();
        messageModel.text = JacobConst.TELEGRAM_RESPONSE.menuMessage;
        messageModel.chat_id = message.message.chat.id;
        MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(messageModel);
        decorator.addButtonsLine(JacobConst.TELEGRAM_COMMANDS.getRecomendation,
                JacobConst.TELEGRAM_COMMANDS.getSetForLearn,
                JacobConst.TELEGRAM_COMMANDS.sendMewWord);
        telegramApi.sendMessage(messageModel);
    }
}
