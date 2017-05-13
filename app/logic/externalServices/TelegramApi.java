package logic.externalServices;

import play.Configuration;
import play.libs.ws.WSClient;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class TelegramApi {
    static String path;
    private final WSClient ws;

        public TelegramApi(WSClient ws, Configuration configuration) {
        this.ws = ws;
        path = configuration.getString("telegram.botApi");
    }


}
