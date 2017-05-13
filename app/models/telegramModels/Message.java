
package models.telegramModels;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Message {

    public int message_id;
    public TelegramUser from;
    public Chat chat;
    public int date;
    public String text;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
