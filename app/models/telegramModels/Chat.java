
package models.telegramModels;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Chat {

    public int id;
    public String first_name;
    public String last_name;
    public String username;
    public String type;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
