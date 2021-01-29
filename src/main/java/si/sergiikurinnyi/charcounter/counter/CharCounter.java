package si.sergiikurinnyi.charcounter.counter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CharCounter implements CharStatCounter {

    @Override
    public Map<Character, Integer> countCharUsage(String sentence) {

        return sentence.chars().mapToObj(symbol -> (char) symbol).collect(
                Collectors.groupingBy(symbol -> symbol, LinkedHashMap::new, Collectors.summingInt(symbol -> 1)));
    }

}