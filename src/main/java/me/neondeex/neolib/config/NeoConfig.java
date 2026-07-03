package me.neondeex.neolib.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;

import java.io.File;

public final class NeoConfig {

    private NeoConfig() {}

    /**
     * Carga (o crea si no existe) una config okaeri desde el archivo dado.
     *
     * Uso:
     * <pre>
     *   MyConfig cfg = NeoConfig.load(MyConfig.class,
     *       new File(getDataFolder(), "config.yml"));
     * </pre>
     */
    public static <T extends OkaeriConfig> T load(Class<T> type, File file) {
        return ConfigManager.create(type, it -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withSerdesPack(new SerdesBukkit());
            it.withBindFile(file);
            it.saveDefaults();
            it.load(true);
        });
    }
}
