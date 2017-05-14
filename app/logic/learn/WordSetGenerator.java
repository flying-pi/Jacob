package logic.learn;

import models.dbModels.UserModel;
import models.dbModels.WordModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yurabraiko on 14.05.17.
 */
public class WordSetGenerator {
    private String userID;

    public WordSetGenerator(String userID){
        this.userID = userID;
    }

    public List<WordModel> getWordSetForUser(){
        UserModel user = UserModel.getUserByID(userID);
        if (user == null)
            return new ArrayList<>();

        List<WordModel> result = new LinkedList<>();
        for(int i=0;i<user.learnedDictionary.size();i++){
            if(result.size()>=25)
                break;
            if(user.learnedDictionary.get(i).progress > 0)
                result.add(user.learnedDictionary.get(i));
        }
        return result;
    }


}
