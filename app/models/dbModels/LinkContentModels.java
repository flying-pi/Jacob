package models.dbModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class LinkContentModels {
    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection linkContentModels() {
        return jongo.getCollection("LinkContentModels");
    }

    static {

        Map<String, Integer> map = new HashMap<>();
        map.put("url", 1);

        linkContentModels().getDBCollection().createIndex(new BasicDBObject(map), null, true);
    }


    @JsonProperty("_id")
    public ObjectId id;

    public String url;

    public String content;

    public LinkContentModels insert() {
        linkContentModels().save(this);
        return this;
    }
}
