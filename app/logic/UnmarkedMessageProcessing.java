package logic;

import helper.MessageButtonDecorator;
import logic.externalServices.TelegramApi;
import models.dbModels.UserModel;
import models.dbModels.WordModel;
import models.telegramModels.IncomingBotMessage;
import models.telegramModels.SendMessageModel;
import play.Configuration;
import play.libs.ws.WSClient;

import static logic.Utils.wordCorelation;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class UnmarkedMessageProcessing {
    TelegramApi telegramApi;

    public static boolean isUnmarkMessage(IncomingBotMessage message) {
        return true;
    }

    private final WSClient ws;
    private final Configuration configuration;

    public UnmarkedMessageProcessing(WSClient ws, Configuration configuration) {
        this.ws = ws;
        this.configuration = configuration;
        telegramApi = new TelegramApi(ws, configuration);
    }

    public void process(IncomingBotMessage message) {
        UserModel userModel = UserModel.getUserByID(String.valueOf(message.message.from.id));
        if (userModel == null)
            return;
        if (userModel.lastWordIndex == -1)
            return;
        WordModel word = userModel.learnedDictionary.get(userModel.lastWordIndex);
        double k = 1 - wordCorelation(word.wordEng, message.message.text);

        word.progress = (1.0 - JacobConst.WORD_LEARN_GROW_UP) * word.progress + JacobConst.WORD_LEARN_GROW_UP * k;
        word.update();
        userModel.update();

        SendMessageModel result = new SendMessageModel();
        result.chat_id = message.message.chat.id;
        result.text = message.message.text.equals(word.wordEng) ?
                JacobConst.TELEGRAM_RESPONSE.correctWord :
                JacobConst.TELEGRAM_RESPONSE.wordWithError + k;

        MessageButtonDecorator decorator = MessageButtonDecorator.typicalDecorator(result);
        decorator.addMenu();
        telegramApi.sendMessage(result);
    }

}
