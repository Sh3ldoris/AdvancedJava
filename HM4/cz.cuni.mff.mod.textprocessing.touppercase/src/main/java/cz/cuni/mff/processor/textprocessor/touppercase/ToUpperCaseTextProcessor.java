package cz.cuni.mff.processor.textprocessor.touppercase;

public class ToUpperCaseTextProcessor implements cz.cuni.mff.processor.textprocessor.definition.TextProcessor {
    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
