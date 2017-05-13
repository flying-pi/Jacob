package logic.textImport;

import logic.JacobConst;
import models.dbModels.WordFrequencyModel;
import models.telegramModels.IncomingBotMessage;
import play.Configuration;
import play.Logger;
import play.libs.ws.WSClient;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static logic.Utils.*;
import static logic.Utils.sortByValue;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class TextLoader {

    public static boolean isContainUrl(IncomingBotMessage message) {
        try {
            return message.message.entities.get(0).type.equals(JacobConst.ENTITIES_TYPE.URL);
        } catch (Exception e) {
            return false;
        }
    }

    private final WSClient ws;
    private final Configuration configuration;

    public TextLoader(WSClient ws, Configuration configuration) {
        this.ws = ws;
        this.configuration = configuration;
    }

    public void proccessMessage(IncomingBotMessage message) {
        new ContentLoder(message).start();
    }


    private class ContentLoder extends Thread {
        private final String url;
        private IncomingBotMessage message;
        private String content;

        private Map<String, Double> wordFrequency = new LinkedHashMap<>();

        double maxWordCount = 0;
        double total = 0;


        public ContentLoder(IncomingBotMessage message) {
            this.message = message;

            int from = message.message.entities.get(0).offset;
            int to = message.message.entities.get(0).length + from;

            this.url = message.message.text.substring(from, to);
            this.content = "";
        }

        private void addLine(String line) {
            Arrays.stream(line.split(" ")).forEach(s -> {
                if (s.length() < 2)
                    return;
                s = s.toLowerCase();
                double frequency = 0;
                wordFrequency.putIfAbsent(s, frequency);
                frequency = wordFrequency.get(s) + 1;
                wordFrequency.put(s, frequency);
                if (frequency > maxWordCount) {
                    maxWordCount = frequency;
                }
                total++;
            });
        }

        private double getNormolizeScaleK() {
//            WordSetInfo commonWordInfo = WordSetInfo.findOne(JacobConst.WORD.globalWordSet);
//            if (commonWordInfo == null)
                return (double) ((long) (total / maxWordCount));
//            return commonWordInfo.scaleK;
        }

        private Map<String, Double> normolize(Map<String, Double> words) {
            Map<String, Double> result = new LinkedHashMap<>();
            double k = getNormolizeScaleK();
            words.forEach((s, aDouble) -> result.put(s, (aDouble * k) / total));

            return result;
        }

        private Map<String, Double> normolizeByGlobalIndex(Map<String, Double> words) {

            Map<String, Double> globalFrequance = WordFrequencyModel.find(words.keySet());
            Map<String, Double> result = new LinkedHashMap<>();
            words.forEach((s, aDouble) -> {
                if (!globalFrequance.containsKey(s)) {
                    result.put(s, aDouble);
                    return;
                }
                Double globaWordFrequency = globalFrequance.get(s);
                double newFrequency = ((aDouble - globaWordFrequency) / aDouble) * (1 - globaWordFrequency);
                result.put(s, newFrequency);
            });
            return result;
        }

        @Override
        public void run() {
            this.content = clear(getTextByUrl(url));
            addLine(content);
            Map<String, Double> normolizedWord = sortByValue(normolize(wordFrequency), (o1, o2) -> {
                if (o1.equals(o2))
                    return 0;
                return o1 > o2 ? -1 : 1;
            });
            double k = getNormolizeScaleK();
            Map<String, Double> globalNormolizeWord = normolizeByGlobalIndex(normolizedWord);
            globalNormolizeWord = sortByValue(globalNormolizeWord,(o1, o2) -> {
                if (o1.equals(o2))
                    return 0;
                return o1 > o2 ? -1 : 1;
            });
            globalNormolizeWord.forEach((s, aDouble) -> System.out.println(s + " => " + aDouble));
            Logger.info("sd");

//            LinkContentModels content = new LinkContentModels();
//            content.content =
//            content.url = url;
//            //todo check  when model is exist in db
//            try {
//                content.insert();
//            } catch (Exception e) {
//
//            }
        }
    }
}
