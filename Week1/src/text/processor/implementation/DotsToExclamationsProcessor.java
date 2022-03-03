package text.processor.implementation;

import text.processor.definition.TextProcessor;

public class DotsToExclamationsProcessor implements TextProcessor {
    @Override
    public String process(String input) {
        return input.replaceAll("\\.", "!");
    }
}
