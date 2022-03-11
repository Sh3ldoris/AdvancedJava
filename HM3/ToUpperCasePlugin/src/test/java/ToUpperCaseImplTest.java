import cz.cuni.mff.java.plugins.textprocessor.TextProcessor;
import cz.cuni.mff.java.plugins.textprocessor.processors.ToUpperCaseImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToUpperCaseImplTest {

    @Test
    public void AllCapitalProcessorTest() {
        TextProcessor processor = new ToUpperCaseImpl();
        assertEquals("AHOJ, SVETE!", processor.process("Ahoj, svete!"));
    }
}
