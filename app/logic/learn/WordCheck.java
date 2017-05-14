package logic.learn;

import models.dbModels.UserModel;
import models.dbModels.WordModel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class WordCheck {
    private String userID;

    public WordCheck(String userID) {
        this.userID = userID;
    }

    @Nullable
    public WordModel getWord() {
        UserModel user = UserModel.getUserByID(userID);
        if (user == null)
            return null;

        List<WordModel> words = new ArrayList<>();
        if (user.learnedDictionary == null || user.learnedDictionary.size() == 0)
            return null;
        for (int i = 0; i < user.learnedDictionary.size(); i++) {
            if (user.learnedDictionary.get(i).progress > 0) {
                double k = (1 - user.learnedDictionary.get(i).progress) * 100;
                if (k < 1)
                    k = 1;
                if (k > 100)
                    k = 100;
                for (int w = 0; w < k; w++) {
                    words.add(user.learnedDictionary.get(i));
                }
            }
        }

        int index = new Random().nextInt(words.size() - 1);
        WordModel result = words.get(index);
        for (int i = 0; i < user.learnedDictionary.size(); i++) {
            if (result.equals(user.learnedDictionary.get(i))) {
                user.lastWordIndex = i;
                user.update();
                break;
            }
        }

        return result;
    }

}
