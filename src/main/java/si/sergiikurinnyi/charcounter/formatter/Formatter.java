package si.sergiikurinnyi.charcounter.formatter;

import java.util.Map;

public class Formatter implements CharFormatter {

    private static final String LINES_FORMAT = "\"%s\" - %s%n";

    @Override
    public String formatStatView(Map<Character, Integer> charToCountNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        charToCountNumber.forEach((symbol, count) -> {
            stringBuilder.append(String.format(LINES_FORMAT, symbol, count));
        });

        return stringBuilder.toString();
    }

}
