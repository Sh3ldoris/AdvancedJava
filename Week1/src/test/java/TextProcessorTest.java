import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import text.processor.definition.TextProcessor;
import text.processor.implementation.AllCapitalProcessor;
import text.processor.implementation.DotsToExclamationsProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextProcessorTest {

    @Test
    @Order(2)
    public void AllCapitalProcessorTest() {
        TextProcessor processor = new AllCapitalProcessor();
        assertEquals("AHOJ, SVETE!", processor.process("Ahoj, svete!"));
    }

    @Test
    @Order(1)
    public void DotsToExclamationsProcessorTest() {
        TextProcessor processor = new DotsToExclamationsProcessor();
        assertEquals("Exam!pl!e !!Text!", processor.process("Exam.pl.e ..Text!"));
    }
}
