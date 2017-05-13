package logic.textImport;

import java.util.Map;

/**
 * Created by yurabraiko on 13.05.17.
 */
public interface ITextAnalayzer {
     void analyze(String input);

    void setResultListener(IOntextAnalyze listener);

     interface IOntextAnalyze {
        void onTextAnayze(Map<String, Double> wordFrequencyMap);
    }
}
