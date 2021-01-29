package si.sergiikurinnyi.charcounter.statcache;

import java.util.Map;

public interface CharStatCache {

    Map<Character, Integer> getFromCache(String key);

    boolean isPresent(String key);

    void putToCache(String key, Map<Character, Integer> value);

}