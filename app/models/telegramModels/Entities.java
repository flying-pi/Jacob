package models.telegramModels;

/**
 * Created by yurabraiko on 13.05.17.
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "offset",
        "length"
})
public class Entities {

    @JsonProperty("type")
    public String type;
    @JsonProperty("offset")
    public int offset;
    @JsonProperty("length")
    public int length;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}