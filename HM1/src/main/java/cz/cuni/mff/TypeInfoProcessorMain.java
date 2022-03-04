package main.java.cz.cuni.mff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class TypeInfoProcessorMain {

    public static void main(String[] args) {
        if (args.length == 0) {
            runWithConsoleInput();
        } else {
            runWithFileInput(args[0]);
        }
    }

    private static void runWithConsoleInput() {
        String lineInput;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                lineInput = br.readLine();
                if (lineInput != null) {
                    printTypeInfo(lineInput);
                } else {
                    break;
                }
            } catch (IOException e) {
                System.out.println("WARNING: Cannot read from the input -> exiting");
                System.exit(1);
            }

        }
    }

    private static void runWithFileInput(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Path.of(fileName));
            for (var line : lines) {
                printTypeInfo(line);
            }
        } catch (IOException e) {
            System.out.println("Cannot read from file with given name -> Exit!");
            System.exit(1);
        }
    }

    private static void printTypeInfo(String name) {
        Class<?> cls;
        try {
            cls = Class.forName(name);
        } catch (ClassNotFoundException e) {
            System.out.println(name + " does not exist\n");
            return;
        }

        System.out.println(cls.getName());
        System.out.println(getClassType(cls));
        System.out.print("Generic: ");
        if (cls.getTypeParameters().length == 0) {
            System.out.println("no");
        } else {
            System.out.print("yes, Variables: ");
            for (var par : cls.getTypeParameters()) {
                System.out.print(par.getName() + " ");
            }
            System.out.print("\n");
        }
        if (cls.getSuperclass() != null) {
            System.out.println(cls.getSuperclass().getName());
        } else {
            System.out.println("null");
        }

        System.out.println(cls.getInterfaces().length);
        for (var intrf : cls.getInterfaces()) {
            System.out.println(intrf.getName());
        }
        System.out.println(cls.getMethods().length);
        int staticMethodsCount = (int) Arrays.stream(cls.getMethods())
                .filter(method -> Modifier.isStatic(method.getModifiers()))
                .count();
        System.out.println(staticMethodsCount);
        System.out.println(cls.getClasses().length);
        for (var innerClazz : cls.getClasses()) {
            System.out.println(innerClazz.getName());
        }
        System.out.print("\n");
    }

    private static String getClassType(Class<?> clazz) {
        if (clazz.isAnnotation()) {
            return "annotation";
        } else if (clazz.isInterface()) {
            return "interface";
        } else if (clazz.isEnum()) {
            return "enum";
        } else if (clazz.isArray()) {
            return "array";
        } else if (clazz.isPrimitive()) {
            return "primitive";
        } else {
            return "class";
        }
    }
}
