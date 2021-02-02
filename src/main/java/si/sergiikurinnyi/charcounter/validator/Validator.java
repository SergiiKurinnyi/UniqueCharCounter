package si.sergiikurinnyi.charcounter.validator;

public class Validator implements SentenceValidator {

    public void validate(String sentence) {
        if (sentence == null) {
            throw new IllegalArgumentException("Sentence is null");
        }
        if (sentence.isEmpty()) {
            throw new IllegalArgumentException("Sentence is empty");
        }
    }

}
