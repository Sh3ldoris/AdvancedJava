package cz.cuni.mff.textprocessor;

import cz.cuni.mff.textprocessor.processor.TextProcessor;
import cz.cuni.mff.textprocessor.service.PluginLoader;
import cz.cuni.mff.textprocessor.service.implementation.ClassPathLoader;
import cz.cuni.mff.textprocessor.service.implementation.RemoteSourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvancedTextProcessorMain {

    public static void main(String[] args) {
        PluginLoader pluginLoader = getPluginLoader(args);
        ServiceLoader<TextProcessor> plugins = pluginLoader.load();

        String lineInput;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                lineInput = br.readLine();
                if (lineInput != null) {
                    for (TextProcessor p : plugins) {
                        lineInput = p.process(lineInput);
                    }

                    System.out.println(lineInput);
                } else {
                    break;
                }
            } catch (IOException e) {
                Logger.getLogger(AdvancedTextProcessorMain.class.getName()).log(Level.WARNING, "WARNING: Cannot read from the input -> exiting");
                System.exit(0);
            }
        }
    }

    private static PluginLoader getPluginLoader(String... args) {
        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Path.of(args[0]));
                return new RemoteSourceLoader(lines.toArray(new String[1]));
            } catch (IOException e) {
                Logger.getLogger(AdvancedTextProcessorMain.class.getName()).log(Level.WARNING, "Cannot load file: {0}", args[0]);
                System.exit(0);
            }
        }
        return new ClassPathLoader();
    }


}
