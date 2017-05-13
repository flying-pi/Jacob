package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by stus on 13.05.17.
 */
public class StatisticModel {
    @JsonProperty("_id")
    public ObjectId id;
    
    public float raitng;
    public Date timeStemp;


    public StatisticModel(float raitng, Date timeStemp) {
        this.raitng = raitng;
        this.timeStemp = timeStemp;
    }

    public StatisticModel() {
    }
}
