package cz.cuni.mff.textprocessor.service.implementation;

import cz.cuni.mff.textprocessor.processor.TextProcessor;
import cz.cuni.mff.textprocessor.service.PluginLoader;

import java.util.ServiceLoader;

public class ClassPathLoader implements PluginLoader {

    @Override
    public ServiceLoader<TextProcessor> load() {
        return ServiceLoader.load(TextProcessor.class);
    }
}
