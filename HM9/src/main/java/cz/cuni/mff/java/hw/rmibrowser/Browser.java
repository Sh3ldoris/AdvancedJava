package cz.cuni.mff.java.hw.rmibrowser;

import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Browser {
    public static void run(String[] argv) {
        try {
            String host = argv.length == 0 ? "localhost" : argv[0];
            String port = argv.length <= 1 ? "1099" : argv[1];
            Registry reg = LocateRegistry.getRegistry(host, Integer.parseInt(port));

            String[] names = reg.list();
            if (names.length == 0) {
                Logger.getLogger(Browser.class.getName()).log(Level.WARNING, "No objects registered");
            } else {
                // Find registered objects
                // Use tree map for sorting by key (name)
                TreeMap<String, Object> objects = new TreeMap<>();
                for (String name : names) {
                    Object obj = reg.lookup(name);
                    objects.put(name, obj);
                }

                // Print output
                objects.forEach((key, value) -> {
                    System.out.println(key);
                    List<String> methods = new ArrayList<>();
                    loadMethods(value.getClass(), methods);
                    methods.sort(null);
                    for (String m : methods) {
                        System.out.println("  " + m);
                    }
                });
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, "Exception occurred", ex);
        }
    }

    private static void loadMethods(Class<?> cls, List<String> methods) {
        for (Class<?> iface : cls.getInterfaces()) {
            methods.addAll(Arrays.stream(iface.getDeclaredMethods()).map(Method::toString).toList());
            loadMethods(iface, methods);
        }
    }
}
