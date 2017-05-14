package models.android;

import com.fasterxml.jackson.annotation.JsonInclude;
import models.dbModels.WordModel;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class RecommendationWord {
    public String id;
    public String word;

    @JsonInclude(NON_NULL)
    public String translate;

    public RecommendationWord(WordModel wordModel) {
        this.id = wordModel.id.toString();
        this.word = wordModel.wordEng;
        if(wordModel.wordRus!=null && wordModel.wordRus.length()>1)
            this.translate = wordModel.wordRus;
    }
}
