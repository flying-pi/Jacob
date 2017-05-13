package logic;
/**
 * Created by yurabraiko on 13.05.17.
 */
public class JacobConst {
    public static class ENTITIES_TYPE{
        public static final  String URL = "url";
        public static final  String BOT_COMMAND = "bot_command";
    }

    public static class WORD{
        public static final int globalWordSet= 1;
    }

    public static class TELEGRAM_RESPONSE{
        public static final String loadedData = "Данные были отправлены на обработку, " +
                "через некоторое время Ваш словарный запас пополнится";
        public static final String menuMessage = "Выберете пожалуйста пункты меню";
        public static final String recomendationListTitle = "Отмете пожалуйста слова для изучения";
    }

    public class TELEGRAM_COMMANDS {

        public static final String menu = "/menu";
        public static final String getRecomendation = "/getRecommendation";
        public static final String getSetForLearn = "/getWordSet";
        public static final String reomendPrefix = "/recommend";
    }
}
