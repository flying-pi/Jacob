package models.telegramModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anatoly on 13.05.17.
 */
public class SendMessageModel {
    public int chat_id;
    public String text;
//    когда есть неиспользуемые поля, то не работает, по этому пока закоментил.
//    public String parse_mode;
//    public boolean disable_web_page_preview;
//    public boolean disable_notification;
//    public int reply_to_message_id;
    public Keyboard reply_markup;

    public SendMessageModel() {
        reply_markup = new Keyboard();
    }

    public SendMessageModel(int chat_id, String text) {
        this();
        this.chat_id = chat_id;
        this.text = text;
    }

    public static class Keyboard {
        public List<List<String>> keyboard;

        public Keyboard(){
            keyboard = new ArrayList<>();
        }

        public void addNewLine(ArrayList<String> newLine){
            keyboard.add(newLine);
        }
    }
}
