package si.sergiikurinnyi.charcounter.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ValidatorTest {

    private static final String EMPTY_INPUT = "";
    private static final String INPUT_NUMBERS = "123 12333";


    private final Validator sentenceValidator = new Validator();

    @Test
    void validateShouldThrowIllegalArgumentExceptionIfArgumentIsNull() {
        String expected = "Sentence is null";
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> sentenceValidator.validate(null));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionIfArgumentIsEmpty() {
        String expected = "Sentence is empty";
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> sentenceValidator.validate(EMPTY_INPUT));
        assertEquals(expected, exception.getMessage());
    }
    
    @Test
    void validateShouldNotThrowExceptionIfArgumentIsNumbers() {
    	assertDoesNotThrow(() -> sentenceValidator.validate(INPUT_NUMBERS));
    }
    
}
