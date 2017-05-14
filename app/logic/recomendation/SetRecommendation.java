package logic.recomendation;

import models.dbModels.UserModel;
import models.dbModels.WordModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class SetRecommendation {
    private final UserModel user;
    private String userID;

    public SetRecommendation(String userID) {
        this.userID = userID;

        this.user = UserModel.getUserByID(userID);
    }

    public void setRecomendationList(String wordID) {
        List<String> singleIDList = new LinkedList<>();
        singleIDList.add(wordID);
        setRecomendationList(singleIDList);
    }

    public void setRecomendationList(List<String> wordIDs) {
        if (user == null)
            return;
        for (int i = 0; i < user.learnedDictionary.size() && wordIDs.size() > 0; i++) {
            WordModel word = user.learnedDictionary.get(i);
            if (word.id.toString().equals(wordIDs.get(0))) {
                if (word.progress <= 0) {
                    word.progress = 0.001d;
                    word.wordRus = "перевод" + word.wordEng;
                    word.update();
                    user.update();
                }
                i = -1;
                break;
            }
        }
    }


}
