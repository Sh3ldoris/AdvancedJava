import cz.cuni.mff.textprocessor.processor.TextProcessor;
import cz.cuni.mff.textprocessor.processor.ToUpperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToUpperCaseImplTest {

    @Test
    public void AllCapitalProcessorTest() {
        TextProcessor processor = new ToUpperImpl();
        assertEquals("AHOJ, SVETE!", processor.process("Ahoj, svete!"));
    }
}
