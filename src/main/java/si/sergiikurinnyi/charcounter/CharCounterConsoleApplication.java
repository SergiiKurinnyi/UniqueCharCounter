package si.sergiikurinnyi.charcounter;

import java.util.Scanner;

import si.sergiikurinnyi.charcounter.counter.CharCounter;
import si.sergiikurinnyi.charcounter.counter.CharStatCounter;
import si.sergiikurinnyi.charcounter.formatter.CharFormatter;
import si.sergiikurinnyi.charcounter.formatter.Formatter;
import si.sergiikurinnyi.charcounter.statcache.CharLRUCache;
import si.sergiikurinnyi.charcounter.statcache.CharStatCache;
import si.sergiikurinnyi.charcounter.statprovider.CharStatProvider;
import si.sergiikurinnyi.charcounter.validator.SentenceValidator;
import si.sergiikurinnyi.charcounter.validator.Validator;

public class CharCounterConsoleApplication {

    public static void main(String[] args) {

        final SentenceValidator sentenceValidator = new Validator();
        final CharStatCache charStatCache = new CharLRUCache(50);
        final CharStatCounter charStatCounter = new CharCounter();
        final CharFormatter charStatViewFormatter = new Formatter();
        final CharStatProvider characterStatisticProvider = new CharStatProvider(sentenceValidator,
                charStatCache, charStatCounter, charStatViewFormatter);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type-in your text");
        String text = scanner.nextLine();
        String result = characterStatisticProvider.provideCharStat(text);
        System.out.println(result);
        scanner.close();
    }

}