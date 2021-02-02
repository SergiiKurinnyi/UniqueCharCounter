package si.sergiikurinnyi.charcounter.counter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CharCounterTest {

    private static final String INPUT_NUMBERS = "123 12333";
    private static final String INPUT_WORDS_WITH_SPECIAL_CHARS_AND_NUMBERS = "1earnf?s1errrr1?1?";

    private final CharCounter charCounter = new CharCounter();

    @Test
    void countCharUsageShouldReturnResultWhenArgumentIsWordsWithSpecialCharsAndNumbers() {
        Map<Character, Integer> expected = new LinkedHashMap<>();
        expected.put('1', 4);
        expected.put('e', 2);
        expected.put('a', 1);
        expected.put('r', 5);
        expected.put('n', 1);
        expected.put('f', 1);
        expected.put('?', 3);
        expected.put('s', 1);

        Map<Character, Integer> actual = new LinkedHashMap<>(
                charCounter.countCharUsage(INPUT_WORDS_WITH_SPECIAL_CHARS_AND_NUMBERS));
        assertThat(actual, equalTo(expected));
    }

    @Test
    void countCharUsageShouldReturnResultWhenArgumentIsNumbers() {
        Map<Character, Integer> expected = new LinkedHashMap<>();
        expected.put('1', 2);
        expected.put('2', 2);
        expected.put('3', 4);
        expected.put(' ', 1);

        Map<Character, Integer> actual = new LinkedHashMap<>(charCounter.countCharUsage(INPUT_NUMBERS));
        assertThat(actual, equalTo(expected));
    }

}
