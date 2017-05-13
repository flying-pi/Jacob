package models.android;

import models.dbModels.WordModel;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class RecommendationWord {
    public String id;
    public String word;

    public RecommendationWord(WordModel wordModel) {
        this.id = wordModel.id.toString();
        this.word = wordModel.wordEng;
    }
}
