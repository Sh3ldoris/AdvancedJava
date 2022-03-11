import cz.cuni.mff.java.plugins.textprocessor.TextProcessor;
import cz.cuni.mff.java.plugins.textprocessor.processors.DotsToExclamationsImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DotsToExclamationsImplTest {

    @Test
    public void DotsToExclamationsProcessorTest() {
        TextProcessor processor = new DotsToExclamationsImpl();
        assertEquals("Exam!pl!e !!Text!", processor.process("Exam.pl.e ..Text!"));
    }
}
