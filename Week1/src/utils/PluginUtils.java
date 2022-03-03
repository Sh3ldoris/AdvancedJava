package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginUtils {

    public static <T> List<T> LoadPlugins(Class<T> pluginInterface, String... pluginNames) {
        List<T> plugins = new ArrayList<>();

        for (String pluginName : pluginNames) {
            try {
                Class<?> cls = Class.forName(pluginName);
                //Check if cls is Class
                if (cls.isAnnotation() || cls.isArray() || cls.isInterface() || cls.isEnum() || cls.isPrimitive()) {
                    Logger.getLogger(PluginUtils.class.getName()).log(Level.WARNING, "{0} is not a class", pluginName);
                    continue;
                }

                //Check if cls implements pluginInterface
                if (!pluginInterface.isAssignableFrom(cls)) {
                    Logger.getLogger(PluginUtils.class.getName()).log(Level.WARNING, "{0} does not implement {1}", new Object[] {pluginName, pluginInterface});
                    continue;
                }
                plugins.add(pluginInterface.cast(cls.getDeclaredConstructor().newInstance()));
            } catch (Exception e) {
                Logger.getLogger(PluginUtils.class.getName()).log(Level.SEVERE, "{0}", e.getMessage());
            }
        }

        return plugins;
    }
}
