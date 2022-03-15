package cz.cuni.mff;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ClassAnnotationsDescriptor {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.exit(0);
        }

        String name = args[0];
        Class<?> cls;
        try {
            cls = Class.forName(name);
        } catch (ClassNotFoundException e) {
            System.out.println(name + " does not exists\n");
            return;
        }

        Map<String, List<Annotation>> mtds = Collections.synchronizedSortedMap(new TreeMap<>());

        while (!cls.getName().equals("java.lang.Object")) {
            for (Class<?> clazz : cls.getInterfaces()) {
                for (Method m : clazz.getMethods()) {
                    if (!mtds.containsKey(m.getName())) {
                        mtds.put(m.getName(), new ArrayList<>(Arrays.stream(m.getAnnotations()).toList()));
                    } else {
                        mtds.get(m.getName()).addAll(Arrays.stream(m.getAnnotations()).toList());
                    }
                }
            }
            for (Method m : cls.getDeclaredMethods()) {
                if (!Modifier.isAbstract(m.getModifiers())) {
                    if (!mtds.containsKey(m.getName())) {
                        mtds.put(m.getName(), new ArrayList<>(Arrays.stream(m.getAnnotations()).toList()));
                    } else {
                        mtds.get(m.getName()).addAll(Arrays.stream(m.getAnnotations()).toList());
                    }
                }
            }
            cls = cls.getSuperclass();
        }

        boolean isNoAnnotations = true;
        for (var annotations : mtds.values()) {
            if (!annotations.isEmpty()) {
                isNoAnnotations = false;
                break;
            }
        }

        if (isNoAnnotations) {
            System.out.println("No annotations in \"" + name + "\"");
            return;
        }

        System.out.printf("Annotations in \"%s\":\n", name);

        for (var entity : mtds.entrySet()) {
            if (entity.getValue().isEmpty()) {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for (Annotation a : entity.getValue()) {
                sb.append(String.format("@%s ", a.annotationType().getName()));
            }
            System.out.printf("%s%s()\n", sb, entity.getKey());
        }
    }
}
