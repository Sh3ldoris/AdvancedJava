package cz.cuni.mff.textprocessor.service;

import cz.cuni.mff.textprocessor.processor.TextProcessor;

import java.util.ServiceLoader;

public interface PluginLoader {

    ServiceLoader<TextProcessor> loadPlugins();

    void setLoadingPath(String path);
}
