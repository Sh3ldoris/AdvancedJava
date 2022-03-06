package cz.cuni.mff.textprocessor.service.implementation;

import cz.cuni.mff.textprocessor.processor.TextProcessor;
import cz.cuni.mff.textprocessor.service.PluginLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteSourceLoader implements PluginLoader {

    private String[] urlsPaths;

    public RemoteSourceLoader(String[] urlsPaths) {
        this.urlsPaths = urlsPaths;
    }

    @Override
    public ServiceLoader<TextProcessor> load() {
        if (urlsPaths == null) {
            throw new IllegalArgumentException("Url paths are not set!");
        }
        List<URL> urls = getUrls(urlsPaths);
        return ServiceLoader.load(TextProcessor.class, new URLClassLoader(urls.toArray(new URL[1])));
    }

    private List<URL> getUrls(String... paths) {
        List<URL> urls = new ArrayList<>();
        for (String path : paths) {
            try {
                urls.add(new URL(path));
            } catch (MalformedURLException e) {
                Logger.getLogger(RemoteSourceLoader.class.getName())
                        .log(Level.WARNING,"Skipping path: {0}", path);
            }
        }

        return urls;
    }
}
