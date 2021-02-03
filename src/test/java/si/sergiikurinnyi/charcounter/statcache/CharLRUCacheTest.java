package si.sergiikurinnyi.charcounter.statcache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class CharLRUCacheTest {

    private static final String INPUT_SMALL_STRING = "f";
    
    private final CharLRUCache charCache = new CharLRUCache(3);

    @Test
    void isPresentShouldReturnFalseForOldestArgumentWhenCacheSizeIs3ButArgumentSizeIs4() {
        charCache.put("a", new LinkedHashMap<Character, Integer>('a', 1));
        charCache.put("b", new LinkedHashMap<Character, Integer>('b', 1));
        charCache.put("c", new LinkedHashMap<Character, Integer>('c', 1));
        charCache.put("d", new LinkedHashMap<Character, Integer>('d', 1));
        assertThat(charCache.isPresent("a"), is(false));
    }
    
    @Test
    void isPresentShouldReturnTrueIfCacheContainsArgument() {
        Map<Character, Integer> charUsage = new LinkedHashMap<>();
        charUsage.put('f', 1);
        charCache.putToCache(INPUT_SMALL_STRING, charUsage);
        assertThat(charCache.isPresent(INPUT_SMALL_STRING), is(true));
    }

    @Test
    void getFromCacheShouldReturnEqualMapIfCacheContainsArgument() {
        Map<Character, Integer> expectedMap = new LinkedHashMap<>();
        expectedMap.put('f', 1);
        charCache.putToCache("f", expectedMap);
        Map<Character, Integer> actualMap = charCache.getFromCache("f");
        assertThat(expectedMap, equalTo(actualMap));
    }

}
