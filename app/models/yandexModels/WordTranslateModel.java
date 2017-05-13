package models.yandexModels;

import java.util.List;

/**
 * Created by sasha on 5/13/17.
 */
public class WordTranslateModel {

    public int code;
    public String lang;
    public List<String> text;

    public WordTranslateModel() {
    }

    public WordTranslateModel(List<String>text, String lang) {
        this.text = text;
        this.lang = lang;
    }

}
