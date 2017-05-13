
package models.telegramModels;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


public class Message {

    public int message_id;
    public TelegramUser from;
    public Chat chat;
    public int date;
    public String text;
    public List<Entities> entities;

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
