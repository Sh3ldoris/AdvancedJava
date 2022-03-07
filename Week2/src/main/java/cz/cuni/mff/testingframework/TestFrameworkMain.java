package cz.cuni.mff.testingframework;

import cz.cuni.mff.testingframework.anotation.*;
import cz.cuni.mff.testingframework.model.TestClassInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestFrameworkMain {

    private static class ClassInfo {
        private Priority priority;
        private Class<?> clazz;
        private List<Method> before;
        private List<Method> test;
        private List<Method> after;

        private String createdBy;
        private String lastModified;

        public ClassInfo(TesterInfo ti, Class<?> clazz, List<Method> before, List<Method> after, List<Method> test) {
            this.priority = ti.priority();
            this.clazz = clazz;
            this.before = before;
            this.after = after;
            this.test = test;
            this.createdBy = ti.createdBy();
            this.lastModified = ti.lastModified();
        }

        public ClassInfo(Priority priority, Class<?> clazz, List<Method> before, List<Method> after, List<Method> test, String createdBy, String lastModified) {
            this.priority = priority;
            this.clazz = clazz;
            this.before = before;
            this.after = after;
            this.test = test;
            this.createdBy = createdBy;
            this.lastModified = lastModified;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length == 0) {
            Logger.getLogger(TestFrameworkMain.class.getName()).log(Level.WARNING, "Nothing to test, exiting!");
            System.exit(0);
        }

        List<ClassInfo> classes = new ArrayList<>();
        List<TestClassInfo> classesToTest = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(args[0]));
            for (var line : lines) {
                Class<?> clazz;
                try {
                    clazz = Class.forName(line);
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(TestFrameworkMain.class.getName()).log(Level.WARNING, "Unknown class, skipping -> {0}", line);
                    continue;
                }

                List<Method> beforeMethods = new ArrayList<>();
                List<Method> afterMethods = new ArrayList<>();
                List<Method> testMethods = new ArrayList<>();

                for (Method m : clazz.getMethods()) {
                    if (m.isAnnotationPresent(Before.class)) {
                        beforeMethods.add(m);
                    } else if (m.isAnnotationPresent(After.class)) {
                        afterMethods.add(m);
                    } else if (m.isAnnotationPresent(Test.class)) {
                        testMethods.add(m);
                    }
                }

                if (clazz.isAnnotationPresent(TesterInfo.class)) {
                    classesToTest.add(
                            new TestClassInfo(
                                    clazz,
                                    clazz.getAnnotation(TesterInfo.class),
                                    beforeMethods,
                                    testMethods,
                                    afterMethods
                                    ));
                    classes.add(new ClassInfo(clazz.getAnnotation(TesterInfo.class), clazz, beforeMethods, afterMethods, testMethods));
                } else {
                    classesToTest.add(
                            new TestClassInfo(
                                    clazz,
                                    "N/A",
                                    "N/A",
                                    Priority.LOW,
                                    beforeMethods,
                                    testMethods,
                                    afterMethods
                            ));
                    classes.add(new ClassInfo(Priority.LOW, clazz, beforeMethods, afterMethods, testMethods, "N/A", "N/A"));
                }
            }
        } catch (IOException e) {
            Logger.getLogger(TestFrameworkMain.class.getName()).log(Level.WARNING, "Cannot read from file with given name, exiting! {0}", args[0]);
            System.exit(0);
        }

        classesToTest.sort(Comparator.comparing(TestClassInfo::priority));

        classes.sort(Comparator.comparing(classInfo -> classInfo.priority));

        int failed = 0;
        int passed = 0;

        for (ClassInfo ci : classes) {
            Object object;

            try {
                object = ci.clazz.getConstructor().newInstance();
            } catch (InstantiationError | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                continue;
            }

            for (Method m : ci.test) {
                Test annot = m.getAnnotation(Test.class);
                if (annot.enabled()) {
                    for (Method mb: ci.before) {
                        try {
                            mb.invoke(object);
                        } catch (Throwable e) {
                            System.exit(1);
                        }
                    }

                    try {
                        m.invoke(object);
                        if (annot.expectedExceptions().length == 0) {
                            passed++;
                        } else {
                            failed++;
                        }
                    } catch (Throwable e) {
                        //Spracovanie ocakvanych vynimiek
                        if (e instanceof InvocationTargetException) {
                            boolean ok = false;
                            for (Class<? extends Throwable> exClass : annot.expectedExceptions()) {
                                if (exClass.isAssignableFrom(e.getCause().getClass())) {
                                    passed++;
                                    ok = true;
                                    break;
                                }
                                if (!ok) {
                                    failed++;
                                }
                            }
                        } else {
                            System.out.println("Unexpected exception!");
                        }
                    }

                    for (Method mb: ci.before) {
                        try {
                            mb.invoke(object);
                        } catch (Throwable e) {
                            System.exit(1);
                        }
                    }
                }
            }
        }
    }
}
