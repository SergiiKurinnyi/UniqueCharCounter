package si.sergiikurinnyi.charcounter.statprovider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import si.sergiikurinnyi.charcounter.counter.CharCounter;
import si.sergiikurinnyi.charcounter.formatter.Formatter;
import si.sergiikurinnyi.charcounter.statcache.CharLRUCache;
import si.sergiikurinnyi.charcounter.validator.Validator;

@ExtendWith(MockitoExtension.class)
class CharStatProviderTest {

    private static final String INPUT_WORDS = "learn fasterrrr";
    private static final String INPUT_WORDS_UPPER_CASE = "LEARN FASTERRRR";
    private static final String INPUT_NUMBERS = "123 12333";
    private static final String INPUT_NUMBERS_AND_CHARS = "learn fasterrrr1111";
    private static final String INPUT_WORDS_WITH_SPECIAL_CHARS = "learn! fasterrrr!!";
    private static final String INPUT_WORDS_WITH_SPECIAL_CHARS_AND_NUMBERS = "1earnf?s1errrr1?1?";

    @Mock
    private Validator sentenceValidatorMock;

    @Mock
    private CharLRUCache charCacheMock;

    @Mock
    private CharCounter charCounterMock;

    @Mock
    private Formatter viewFormatterMock;
    
    @InjectMocks
    private CharStatProvider charStatProviderMock;
    
    private final Validator sentenceValidator = new Validator();
    private final CharLRUCache charCache = new CharLRUCache(50);
    private final CharCounter charCounter = new CharCounter();
    private final Formatter viewFormatter = new Formatter();
    private final CharStatProvider charStatProvider = new CharStatProvider(sentenceValidator, charCache,
            charCounter, viewFormatter);
    private final Map<Character, Integer> symbolToCount = new LinkedHashMap<>();

    @Test
    void provideCharStatShouldCallValidateIsPresentCountCharUsagePutToCacheFormatStatView() {
        charStatProviderMock.provideCharStat(INPUT_WORDS);
        Mockito.verify(sentenceValidatorMock).validate(INPUT_WORDS);
        Mockito.verify(charCacheMock).isPresent(INPUT_WORDS);
        Mockito.verify(charCounterMock).countCharUsage(INPUT_WORDS);
        Mockito.verify(charCacheMock).putToCache(INPUT_WORDS, symbolToCount);
        Mockito.verify(viewFormatterMock).formatStatView(symbolToCount);
    }

    @Test
    void provideCharStatShouldReturnresultIfArgumentIsWords() {
        String expected = "\"l\" - 1\r\n" + "\"e\" - 2\r\n" + "\"a\" - 2\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
                + "\" \" - 1\r\n" + "\"f\" - 1\r\n" + "\"s\" - 1\r\n" + "\"t\" - 1\r\n";
        String actual = charStatProvider.provideCharStat(INPUT_WORDS);
        assertEquals(expected, actual);
    }

    @Test
    void provideCharStatShouldReturnResultIfArgumentIsWordsUpperCase() {
        String expected = "\"L\" - 1\r\n" + "\"E\" - 2\r\n" + "\"A\" - 2\r\n" + "\"R\" - 5\r\n" + "\"N\" - 1\r\n"
                + "\" \" - 1\r\n" + "\"F\" - 1\r\n" + "\"S\" - 1\r\n" + "\"T\" - 1\r\n";
        String actual = charStatProvider.provideCharStat(INPUT_WORDS_UPPER_CASE);
        assertEquals(expected, actual);
    }

    @Test
    void provideCharStatShouldReturnResultIfArgumentIsNumbersAndChars() {
        String expected = "\"l\" - 1\r\n" + "\"e\" - 2\r\n" + "\"a\" - 2\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
                + "\" \" - 1\r\n" + "\"f\" - 1\r\n" + "\"s\" - 1\r\n" + "\"t\" - 1\r\n" + "\"1\" - 4\r\n";
        String actual = charStatProvider.provideCharStat(INPUT_NUMBERS_AND_CHARS);
        assertEquals(expected, actual);
    }

    @Test
    void provideCharStatShouldReturnResultIfArgumentIsWordsWithSpecialChars() {
        String expected = "\"l\" - 1\r\n" + "\"e\" - 2\r\n" + "\"a\" - 2\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
                + "\"!\" - 3\r\n" + "\" \" - 1\r\n" + "\"f\" - 1\r\n" + "\"s\" - 1\r\n" + "\"t\" - 1\r\n";
        String actual = charStatProvider.provideCharStat(INPUT_WORDS_WITH_SPECIAL_CHARS);
        assertEquals(expected, actual);
    }

    @Test
    void provideCharStatShouldReturnResultIfArgumentIsWordsWithSpecialCharsAndNumbers() {
        String expected = "\"1\" - 4\r\n" + "\"e\" - 2\r\n" + "\"a\" - 1\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
                + "\"f\" - 1\r\n" + "\"?\" - 3\r\n" + "\"s\" - 1\r\n";
        String actual = charStatProvider
                .provideCharStat(INPUT_WORDS_WITH_SPECIAL_CHARS_AND_NUMBERS);
        assertEquals(expected, actual);
    }

    @Test
    void provideCharStatShouldReturnResultIfArgumentIsNumbers() {
        String expected = "\"1\" - 2\r\n" + "\"2\" - 2\r\n" + "\"3\" - 4\r\n" + "\" \" - 1\r\n";
        String actual = charStatProvider.provideCharStat(INPUT_NUMBERS);
        assertEquals(expected, actual);
    }

}