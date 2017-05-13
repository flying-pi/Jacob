package logic.statistics;


import models.dbModels.StatisticModel;
import models.dbModels.UserModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by stus on 11.05.17.
 */
public class Statistic {

    public static void updateStatistic(String id) {
//        UserModel userModel = new UserModel();
//        userModel = userModel.getUserById(id);
//        DictionaryModel dictionaryModel = userModel.dictionary;
//        List<StatisticModel> statisticList = userModel.statistic;
//        float wordRaiting = 0f;
//        float rating;
//        for (int i = 0; i < dictionaryModel.learnedDictionary.size(); i++) {
//            float wordFrequency = dictionaryModel.learnedDictionary.get(i).frequency;
//            float wordUsage = dictionaryModel.learnedDictionary.get(i).usage;
//            wordRaiting += wordFrequency * wordUsage;
//        }
//        rating = new BigDecimal(wordRaiting / dictionaryModel.learnedDictionary.size()).setScale(3, RoundingMode.UP).floatValue();
//        statisticList.add(new StatisticModel(rating, new Date()));
//        userModel.updateUser(userModel);
    }

    public static String getStatistic(String id) {
        UserModel userModel = new UserModel();
        userModel = userModel.getUserById(id);
        List<StatisticModel> statisticList = userModel.statistic;
        String statistic = "";
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");

        for (int i = 0; i < statisticList.size(); i++) {
            statistic += sdfr.format(statisticList.get(i).timeStemp) + statisticList.get(i).raitng+"\n";
        }
        return statistic;
    }
}
