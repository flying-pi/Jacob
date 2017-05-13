package models.yandexModels;

/**
 * Created by sasha on 5/13/17.
 */
public class LanguageDetectedModel {

    public int code;
    public String lang;


    public LanguageDetectedModel() {
    }

    public LanguageDetectedModel(int code, String lang) {
        this.code = code;
        this.lang = lang;
    }

}
