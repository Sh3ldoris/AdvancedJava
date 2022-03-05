package cz.cuni.mff.java.plugins;

import cz.cuni.mff.java.plugins.textprocessor.TextProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextProcessorMain {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.exit(0);
        }

        String directory = args[0];
        File[] files = loadDirectoryFiles(directory);

        if (files == null || files.length == 0) {
            Logger.getLogger(TextProcessorMain.class.getName()).log(Level.WARNING, "Cannot find any files here: {0}", directory);
            System.exit(0);
        }

        List<URL> urls = loadUrls(files);
        ServiceLoader<TextProcessor> loader = loadTextProcessors(urls.toArray(new URL[1]));

        String lineInput;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                lineInput = br.readLine();
                if (lineInput != null) {
                    for (TextProcessor p : loader) {
                        lineInput = p.process(lineInput);
                    }

                    System.out.println(lineInput);
                } else {
                    break;
                }
            } catch (IOException e) {
                Logger.getLogger(TextProcessorMain.class.getName()).log(Level.WARNING, "WARNING: Cannot read from the input -> exiting");
                System.exit(0);
            }

        }
    }

    private static File[] loadDirectoryFiles(String dirName) {
        try {
            File folder = new File(dirName);
            return folder.listFiles();
        } catch (NullPointerException | SecurityException e) {
            Logger.getLogger(TextProcessorMain.class.getName()).log(Level.WARNING, "Cannot find given folder! ", dirName);
        }

        return null;
    }

    private static List<URL> loadUrls(File... files) {
        List<URL> urls = new ArrayList<>();
        for(File file : files) {
            try {
                if (file.getName().endsWith(".jar")) {
                    urls.add(file.toURI().toURL());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    private static ServiceLoader<TextProcessor> loadTextProcessors(URL... urls) {
        return ServiceLoader.load(TextProcessor.class, new URLClassLoader(urls));
    }
}
