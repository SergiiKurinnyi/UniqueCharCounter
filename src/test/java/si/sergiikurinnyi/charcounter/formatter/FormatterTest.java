package si.sergiikurinnyi.charcounter.formatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class FormatterTest {

    private final Formatter viewFormatter = new Formatter();

    @Test
    void formatStatViewShouldReturnResultIfArgumentIsWordsWithNumbersAndSpecialChars() {
        Map<Character, Integer> inputMap = new LinkedHashMap<>();
        inputMap.put('1', 4);
        inputMap.put('e', 2);
        inputMap.put('a', 1);
        inputMap.put('r', 5);
        inputMap.put('n', 1);
        inputMap.put('f', 1);
        inputMap.put('?', 3);
        inputMap.put('s', 1);
        String actual = viewFormatter.formatStatView(inputMap);
        String expected = "\"1\" - 4\r\n" + "\"e\" - 2\r\n" + "\"a\" - 1\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
                + "\"f\" - 1\r\n" + "\"?\" - 3\r\n" + "\"s\" - 1\r\n";
        assertThat(actual, equalTo(expected));
    }

    @Test
    void formatStatViewShouldReturnResultIfArgumentIsNumbers() {
        Map<Character, Integer> inputMap = new LinkedHashMap<>();
        inputMap.put('1', 2);
        inputMap.put('2', 2);
        inputMap.put('3', 4);
        inputMap.put(' ', 1);

        String actual = viewFormatter.formatStatView(inputMap);
        String expected = "\"1\" - 2\r\n" + "\"2\" - 2\r\n" + "\"3\" - 4\r\n" + "\" \" - 1\r\n";
        assertThat(actual, equalTo(expected));
    }

}