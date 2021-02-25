package si.sergiikurinnyi.charcounter.statcache;

import java.util.LinkedHashMap;
import java.util.Map;

public class CharLRUCache extends LinkedHashMap<String, Map<Character, Integer>> implements CharStatCache {

    private static final long serialVersionUID = 1L;

    private final int capacity;

    public CharLRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public boolean isPresent(String key) {
        return super.containsKey(key);
    }

    public Map<Character, Integer> getFromCache(String key) {
        return super.get(key);
    }

    public void putToCache(String key, Map<Character, Integer> value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Map<Character, Integer>> eldest) {
        return this.size() > capacity;
    }

}
