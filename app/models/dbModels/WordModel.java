package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

/**
 * Created by stus on 13.05.17.
 */
public class WordModel {
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
}
