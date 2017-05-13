package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class WordFrequencyModel {
    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection wordFrequencyModel() {
        return jongo.getCollection("WordFrequencyModel");
    }


    static {
        Map<String, Integer> map = new HashMap<>();
        map.put("word", 1);
        map.put("type", 1);

        wordFrequencyModel().getDBCollection().createIndex(new BasicDBObject(map), null, true);
    }

    @JsonProperty("_id")
    public ObjectId id;

    public int type;

    public String word;

    public double frequency;

    public WordFrequencyModel() {

    }

    public WordFrequencyModel(String word, double frequency, int type) {
        this.word = word;
        this.frequency = frequency;
        this.type = type;
    }

    public WordFrequencyModel insert() {
        wordFrequencyModel().save(this);
        return this;
    }

    @Nullable
    public static WordFrequencyModel findOne(String word) {
        return wordFrequencyModel().findOne("{\"word\": \"" + word + "\"}").as(WordFrequencyModel.class);

    }

    public static Map<String, Double> find(Iterable<String> strings) {
        Map<String, Double> result = new HashMap<>();
        final String[] requset = {""};
        strings.forEach(s -> requset[0] += ", {\"word\": \"" + s +"\"}");
        requset[0] = requset[0].substring(1);
        requset[0] = "{ $or: [" + requset[0] + "] }";

        MongoCursor<WordFrequencyModel> foundResult = wordFrequencyModel().find(requset[0]).as(WordFrequencyModel.class);
        while (foundResult.hasNext()) {
            WordFrequencyModel item = foundResult.next();
            result.put(item.word, item.frequency);
        }
        return result;
    }
}
