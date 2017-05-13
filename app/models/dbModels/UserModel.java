package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stus on 13.05.17.
 */
public class UserModel {
    public UserModel(String userId, String name, String chatId, DictionaryModel dictionary, List<StatisticModel> statistic) {
        this.userId = userId;
        this.name = name;
        this.chatId = chatId;
        this.dictionary = dictionary;
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
    public DictionaryModel dictionary = new DictionaryModel();
    public List<StatisticModel> statistic = new ArrayList<>();

    public UserModel getUserById(String id) {
        return messages().findOne("{\"userId\": \"" + id + "\"}").as(UserModel.class);
    }

    public UserModel insert() {
        messages().save(this);
        return this;
    }

    public UserModel updateUser(UserModel newUserModel) {
        messages().update("{\"userId\": \"" + this.userId + "\"}").with(newUserModel);
        return this;
    }


}
