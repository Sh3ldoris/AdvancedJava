package cz.cuni.mff.textprocessor.processor;

import com.google.auto.service.AutoService;

@AutoService(TextProcessor.class)
public class ToUpperImpl implements TextProcessor {

    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
