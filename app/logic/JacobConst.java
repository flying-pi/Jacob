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
        public static final String recomendationFinish = "Выбор рекомендаций завершон";
        public static final String allWordSetTitle = "Ваш список слов для изучения";
        public static final String emptySet = "К сожалению у вас не выбрано слов для изучения...";
        public static final String enterTranslate = "Ввидите перевод для: ";
    }

    public class TELEGRAM_COMMANDS {

        public static final String menu = "/menu";
        public static final String getRecomendation = "/getRecommendation";
        public static final String getSetForLearn = "/showWordSet";
        public static final String reomendPrefix = "/recommend";
        public static final String sendMewWord = "/sendWord";
    }
}
