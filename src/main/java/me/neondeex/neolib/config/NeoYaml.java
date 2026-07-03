package me.neondeex.neolib.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NeoYaml {

    private NeoYaml() {
    }

    public static Map<String, FileConfiguration> loadResources(JavaPlugin plugin, List<String> fileNames) {
        Map<String, FileConfiguration> configs = new HashMap<>();
        for (String fileName : fileNames) {
            File file = new File(plugin.getDataFolder(), fileName);
            if (!file.exists()) {
                plugin.saveResource(fileName, false);
            }
            configs.put(fileName, YamlConfiguration.loadConfiguration(file));
        }
        return configs;
    }
}
