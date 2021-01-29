package si.sergiikurinnyi.charcounter.counter;

import java.util.Map;

public interface CharStatCounter {

    Map<Character, Integer> countCharUsage(String sentence);

}