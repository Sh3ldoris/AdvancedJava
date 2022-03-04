package text.processor;

import text.processor.definition.TextProcessor;
import utils.PluginUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class TextEditorMain {

    public static void main(String[] args) {
        List<TextProcessor> processors = Collections.emptyList();
        if (args.length > 0) {
            try {
                List<String> lines = Files.readAllLines(Path.of(args[0]));
                processors = PluginUtils.LoadPlugins(TextProcessor.class, lines.toArray(new String[0]));
            } catch (IOException e) {
                System.out.println("Cannot read from file with given name -> Exit!");
                System.exit(1);
            }
        }

        String inputText;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                inputText = br.readLine();

                if ("exit".equals(inputText)) {
                    System.exit(0);
                }

                for (TextProcessor processor : processors) {
                    inputText = processor.process(inputText);
                }
                System.out.println(inputText);
            } catch (IOException e) {
                System.out.println("WARNING: Cannot read from the input -> exiting");
                System.exit(1);
            }
        }
    }
}
