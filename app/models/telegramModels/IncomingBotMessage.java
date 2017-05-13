
package models.telegramModels;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IncomingBotMessage {

    public int update_id;
    public Message message;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
