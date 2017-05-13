
package models.telegramModels;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TelegramUser {

    public int id;
    public String first_name;
    public String last_name;
    public String username;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
