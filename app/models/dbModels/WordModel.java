package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by stus on 13.05.17.
 */
public class WordModel {


    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection wordModel() {
        return jongo.getCollection("wordModel");
    }

    @JsonProperty("_id")
    public ObjectId id;

    public String wordEng = "";
    public String wordRus = "";
    public double frequency = 0d;
    public double progress = 0d;

    public WordModel(String wordEng, String wordRus, double usage, double frequency) {
        this.wordEng = wordEng;
        this.wordRus = wordRus;
        this.frequency = frequency;
    }

    public WordModel() {
    }

    public WordModel insert() {
        WriteResult result = wordModel().save(this);
        this.id = (ObjectId) result.getUpsertedId();
        return this;
    }
}
