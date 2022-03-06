package cz.cuni.mff.textprocessor;

import cz.cuni.mff.textprocessor.processor.TextProcessor;

import java.io.IOException;
import java.util.ServiceLoader;

public class AdvancedTextProcessorMain {

    public static void main(String[] args) throws IOException {
        /*List<URL> urls = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of("source.txt"));
            for (String line : lines) {
                try {
                    URL url = new URL(line);
                    urls.add(url);
                } catch (MalformedURLException e) {
                    System.out.println("Skipping!");
                    //continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read from file!");
            return;
        }*/

        //ServiceLoader<TextProcessor> loader = ServiceLoader.load(TextProcessor.class, new URLClassLoader(urls.toArray(new URL[1])));
        System.out.println("Testujem!");
        ServiceLoader<TextProcessor> loader = ServiceLoader.load(TextProcessor.class);

        for (TextProcessor p : loader) {
            System.out.println(p.process("Ahoj, svete!"));;
        }
    }
}
