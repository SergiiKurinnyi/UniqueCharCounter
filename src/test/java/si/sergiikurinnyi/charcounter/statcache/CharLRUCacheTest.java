package si.sergiikurinnyi.charcounter.statcache;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import si.sergiikurinnyi.charcounter.counter.CharCounter;
import si.sergiikurinnyi.charcounter.formatter.Formatter;
import si.sergiikurinnyi.charcounter.statprovider.CharStatProvider;
import si.sergiikurinnyi.charcounter.validator.Validator;

@ExtendWith(MockitoExtension.class)
class CharLRUCacheTest {

    private static final String INPUT_SMALL_STRING = "f";

    @Mock
    private CharLRUCache charCacheMock;

    @Mock
    private Validator sentenceValidatorMock;

    @Mock
    private CharCounter charCounterMock;

    @Mock
    private Formatter viewFormatterMock;

    @InjectMocks
    private CharStatProvider charStatProviderMock;

    @Test
    void provideCharStatShouldCallCountCharUsageIfNoArgumentInCache() {
        charStatProviderMock.provideCharStat(INPUT_SMALL_STRING);
        verify(charCounterMock, times(1)).countCharUsage(INPUT_SMALL_STRING);
    }

    @Test
    void provideCharStatShouldNotCallCountUsageIfCacheContainsArgument() {
        when(charCacheMock.isPresent(INPUT_SMALL_STRING)).thenReturn(true);
        charStatProviderMock.provideCharStat(INPUT_SMALL_STRING);
        verify(charCounterMock, times(0)).countCharUsage(INPUT_SMALL_STRING);
    }

}