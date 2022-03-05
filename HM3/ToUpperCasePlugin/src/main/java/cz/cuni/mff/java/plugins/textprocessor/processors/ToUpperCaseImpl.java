package cz.cuni.mff.java.plugins.textprocessor.processors;

import com.google.auto.service.AutoService;
import cz.cuni.mff.java.plugins.textprocessor.TextProcessor;

@AutoService(TextProcessor.class)
public class ToUpperCaseImpl implements TextProcessor {

    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
