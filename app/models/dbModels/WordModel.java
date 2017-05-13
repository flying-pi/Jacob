package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

/**
 * Created by stus on 13.05.17.
 */
public class WordModel {
    @JsonProperty("_id")
    public ObjectId id;
    public String wordEnd = "";
    public String wordRus = "";
    public float usage = 0f;
    public float frequency = 0f;

    public WordModel(String wordEnd, String wordRus, float usage, float frequency) {
        this.wordEnd = wordEnd;
        this.wordRus = wordRus;
        this.usage = usage;
        this.frequency = frequency;
    }

    public WordModel() {
    }
}
