package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.android.InsertTextRequestModel;
import models.telegramModels.IncomingBotMessage;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Logger;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stus on 13.05.17.
 */
public class UserModel {


    public UserModel(String userId, String name, String chatId, List<StatisticModel> statistic) {
        this.userId = userId;
        this.name = name;
        this.chatId = chatId;
        this.statistic = statistic;
    }

    public UserModel() {

    }

    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection users() {
        return jongo.getCollection("users");
    }

    @JsonProperty("_id")
    public ObjectId id;

    public String userId = "";
    public String name = "";
    public String chatId = "";
    public List<StatisticModel> statistic = new ArrayList<>();
    public int lastWordIndex = -1;

    public List<WordModel> learnedDictionary = new ArrayList<>();

    public UserModel getUserById(String id) {
        return users().findOne("{\"userId\": \"" + id + "\"}").as(UserModel.class);
    }

    public void addNewEnglisWord(String word, double frequency) {
        if (learnedDictionary != null) {
            for (WordModel model : learnedDictionary) {
                if (word.equals(model.wordEng))
                    return;
            }
        } else {
            learnedDictionary = new LinkedList<>();
        }
        WordModel newWord = new WordModel();
        newWord.frequency = frequency;
        newWord.wordEng = word;
        newWord = newWord.insert();
        learnedDictionary.add(newWord);
        users().update("{\"userId\": \"" + this.userId + "\"}").with(this);
        Logger.info("adding new word in db");
    }

    public void update() {
        users().update("{\"userId\": \"" + this.userId + "\"}").with(this);
    }

    public UserModel insert() {
        users().save(this);
        return this;
    }

    public static UserModel getUserByChatMessage(IncomingBotMessage botMessage) {
        UserModel result = users().findOne("{\"userId\": \"" + botMessage.message.from.id + "\"}").as(UserModel.class);
        if (result != null)
            return result;
        UserModel newUser = new UserModel();
        newUser.userId = String.valueOf(botMessage.message.from.id);
        newUser.name = botMessage.message.from.first_name + " " + botMessage.message.from.last_name;
        newUser.chatId = String.valueOf(botMessage.message.chat.id);
        return newUser.insert();
    }


    public static UserModel getUserByChatMessage(InsertTextRequestModel request) {
        UserModel result = users().findOne("{\"userId\": \"" + request.userID + "\"}").as(UserModel.class);
        if (result != null)
            return result;
        UserModel newUser = new UserModel();
        newUser.userId = String.valueOf(request.userID);
        newUser.name = request.userName;
        newUser.chatId = String.valueOf(request.chatID);
        return newUser.insert();
    }

    @Nullable
    public static UserModel getUserByID(String userID) {
        return users().findOne("{\"userId\": \"" + userID + "\"}").as(UserModel.class);
    }
}
