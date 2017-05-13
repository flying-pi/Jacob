package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.telegramModels.IncomingBotMessage;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

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

    public static MongoCollection messages() {
        return jongo.getCollection("users");
    }

    @JsonProperty("_id")
    public ObjectId id;

    public String userId = "";
    public String name = "";
    public String chatId = "";
    public List<StatisticModel> statistic = new ArrayList<>();

    public List<WordModel> learnedDictionary = new ArrayList<>();

    public UserModel getUserById(String id) {
        return messages().findOne("{\"userId\": \"" + id + "\"}").as(UserModel.class);
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
        learnedDictionary.add(newWord);
        messages().update("{\"userId\": \"" + this.userId + "\"}").with(this);
    }

    public UserModel insert() {
        messages().save(this);
        return this;
    }

    public static UserModel getUserByChatMessage(IncomingBotMessage botMessage) {
        UserModel result = messages().findOne("{\"userId\": \"" + botMessage.message.from.id + "\"}").as(UserModel.class);
        if (result != null)
            return result;
        UserModel newUser = new UserModel();
        newUser.userId = String.valueOf(botMessage.message.from.id);
        newUser.name = botMessage.message.from.first_name + " " + botMessage.message.from.last_name;
        newUser.chatId = String.valueOf(botMessage.message.chat.id);
        return newUser.insert();
    }


}
