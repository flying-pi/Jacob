package logic;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yurabraiko on 13.05.17.
 */
public class Utils {

    public static String getTextByUrl(String url) {

        String res = "";
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get();
            Elements metaElements = doc.getElementsByTag("div");
            for (Element content : metaElements) {
                res += content.text().replaceAll(",", "").replaceAll("\\.", "").replaceAll(":", "").replaceAll(";", "")
                        .replaceAll("!", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\?", "").replaceAll("\\*", "")
                        .replaceAll("—", "").replaceAll("”", "").replaceAll("“", "");
            }
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String clear(String str) {
        return str
                .replaceAll("[^A-Za-z]", " ")//for english words
//                .replaceAll("[^А-Яа-я]", " ")
                .replaceAll(" {2}", " ")
                .replaceAll("\\b\\w{1,2}\\b\\s?", "")
                ;
    }

    public static <K, V > Map<K, V> sortByValue(Map<K, V> map, Comparator<? super V> comparator) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
