package si.sergiikurinnyi.charcounter.formatter;

import java.util.Map;

public interface CharFormatter {

    String formatStatView(Map<Character, Integer> characterToCountNumber);

}