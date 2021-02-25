package si.sergiikurinnyi.charcounter.statprovider;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
	void validateShouldThrowIllegalArgumentExceptionIfArgumentIsNull() {
		String expected = "Sentence is null";
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> sentenceValidator.validate(null));
		assertThat(expected, equalTo(exception.getMessage()));
	}

	@Test
	void provideCharStatShouldNotCallCountUsageIfCacheContainsArgument() {
		when(charCacheMock.isPresent(INPUT_WORDS)).thenReturn(true);
		charStatProviderMock.provideCharStat(INPUT_WORDS);
		verify(charCounterMock, times(0)).countCharUsage(INPUT_WORDS);
	}

	@Test
	void provideCharStatShouldCallCountCharUsageIfNoArgumentInCache() {
		charStatProviderMock.provideCharStat(INPUT_WORDS);
		verify(charCounterMock).countCharUsage(INPUT_WORDS);
	}

	@Test
	void provideCharStatShouldCallValidateIsPresentCountCharUsagePutToCacheFormatStatView() {
		charStatProviderMock.provideCharStat(INPUT_WORDS);
		verify(sentenceValidatorMock).validate(INPUT_WORDS);
		verify(charCacheMock).isPresent(INPUT_WORDS);
		verify(charCounterMock).countCharUsage(INPUT_WORDS);
		verify(charCacheMock).putToCache(INPUT_WORDS, symbolToCount);
		verify(viewFormatterMock).formatStatView(symbolToCount);
	}

	@Test
	void provideCharStatShouldReturnresultIfArgumentIsWords() {
		String expected = "\"l\" - 1\r\n" + "\"e\" - 2\r\n" + "\"a\" - 2\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
				+ "\" \" - 1\r\n" + "\"f\" - 1\r\n" + "\"s\" - 1\r\n" + "\"t\" - 1\r\n";
		String actual = charStatProvider.provideCharStat(INPUT_WORDS);
		assertThat(actual, equalTo(expected));
	}

	@Test
	void provideCharStatShouldReturnResultIfArgumentIsWordsUpperCase() {
		String expected = "\"L\" - 1\r\n" + "\"E\" - 2\r\n" + "\"A\" - 2\r\n" + "\"R\" - 5\r\n" + "\"N\" - 1\r\n"
				+ "\" \" - 1\r\n" + "\"F\" - 1\r\n" + "\"S\" - 1\r\n" + "\"T\" - 1\r\n";
		String actual = charStatProvider.provideCharStat(INPUT_WORDS_UPPER_CASE);
		assertThat(actual, equalTo(expected));
	}

	@Test
	void provideCharStatShouldReturnResultIfArgumentIsNumbersAndChars() {
		String expected = "\"l\" - 1\r\n" + "\"e\" - 2\r\n" + "\"a\" - 2\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
				+ "\" \" - 1\r\n" + "\"f\" - 1\r\n" + "\"s\" - 1\r\n" + "\"t\" - 1\r\n" + "\"1\" - 4\r\n";
		String actual = charStatProvider.provideCharStat(INPUT_NUMBERS_AND_CHARS);
		assertThat(actual, equalTo(expected));
	}

	@Test
	void provideCharStatShouldReturnResultIfArgumentIsWordsWithSpecialChars() {
		String expected = "\"l\" - 1\r\n" + "\"e\" - 2\r\n" + "\"a\" - 2\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
				+ "\"!\" - 3\r\n" + "\" \" - 1\r\n" + "\"f\" - 1\r\n" + "\"s\" - 1\r\n" + "\"t\" - 1\r\n";
		String actual = charStatProvider.provideCharStat(INPUT_WORDS_WITH_SPECIAL_CHARS);
		assertThat(actual, equalTo(expected));
	}

	@Test
	void provideCharStatShouldReturnResultIfArgumentIsWordsWithSpecialCharsAndNumbers() {
		String expected = "\"1\" - 4\r\n" + "\"e\" - 2\r\n" + "\"a\" - 1\r\n" + "\"r\" - 5\r\n" + "\"n\" - 1\r\n"
				+ "\"f\" - 1\r\n" + "\"?\" - 3\r\n" + "\"s\" - 1\r\n";
		String actual = charStatProvider
				.provideCharStat(INPUT_WORDS_WITH_SPECIAL_CHARS_AND_NUMBERS);
		assertThat(actual, equalTo(expected));
	}

	@Test
	void provideCharStatShouldReturnResultIfArgumentIsNumbers() {
		String expected = "\"1\" - 2\r\n" + "\"2\" - 2\r\n" + "\"3\" - 4\r\n" + "\" \" - 1\r\n";
		String actual = charStatProvider.provideCharStat(INPUT_NUMBERS);
		assertThat(actual, equalTo(expected));
	}

}
