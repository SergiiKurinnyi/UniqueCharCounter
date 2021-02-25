package si.sergiikurinnyi.charcounter.statprovider;

import java.util.Map;

import si.sergiikurinnyi.charcounter.counter.CharStatCounter;
import si.sergiikurinnyi.charcounter.formatter.CharFormatter;
import si.sergiikurinnyi.charcounter.statcache.CharStatCache;
import si.sergiikurinnyi.charcounter.validator.SentenceValidator;

public class CharStatProvider {
    private final SentenceValidator sentenceValidator;
    private final CharStatCache charStatCache;
    private final CharStatCounter charStatCounter;
    private final CharFormatter charStatViewFormatter;

    public CharStatProvider(SentenceValidator sentenceValidator,
            CharStatCache charStatCache, CharStatCounter charStatCounter,
            CharFormatter charStatViewFormatter) {
        this.sentenceValidator = sentenceValidator;
        this.charStatCache = charStatCache;
        this.charStatCounter = charStatCounter;
        this.charStatViewFormatter = charStatViewFormatter;
    }

    public String provideCharStat(String sentence) {
        sentenceValidator.validate(sentence);

        final Map<Character, Integer> symbolToCount;
        if (charStatCache.isPresent(sentence)) {
            symbolToCount = charStatCache.getFromCache(sentence);
        } else {
            symbolToCount = charStatCounter.countCharUsage(sentence);
            charStatCache.putToCache(sentence, symbolToCount);
        }

        return charStatViewFormatter.formatStatView(symbolToCount);
    }

}
