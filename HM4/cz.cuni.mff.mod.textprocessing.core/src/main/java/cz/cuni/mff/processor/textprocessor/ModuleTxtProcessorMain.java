package cz.cuni.mff.processor.textprocessor;

import cz.cuni.mff.processor.textprocessor.definition.TextProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ModuleTxtProcessorMain {

    public static void main(String[] args) {
        ServiceLoader<TextProcessor> sl = ServiceLoader.load(TextProcessor.class);
        try {
            String input;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                input = in.readLine();
                if (input == null || input.equals("exit")) {
                    break;
                }

                for (TextProcessor tp : sl) {
                    input = tp.process(input);
                }
                System.out.println(input);
            }
        } catch (IOException ex) {
            Logger.getLogger(ModuleTxtProcessorMain.class.getName()).log(Level.SEVERE, "IOException", ex);
        }
    }
}
