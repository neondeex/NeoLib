package me.neondeex.neolib;

import com.github.retrooper.packetevents.PacketEvents;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.tofaa.entitylib.APIConfig;
import me.tofaa.entitylib.EntityLib;
import me.tofaa.entitylib.spigot.SpigotEntityLibPlatform;
import me.neondeex.neolib.database.DatabaseConfig;
import me.neondeex.neolib.database.DatabaseManager;
import me.neondeex.neolib.database.MongoManager;
import me.neondeex.neolib.database.NeoDatabase;
import me.neondeex.neolib.database.RedisManager;
import me.neondeex.neolib.config.NeoConfig;
import java.io.File;
import me.neondeex.neolib.hooks.HookManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NeoLib extends JavaPlugin {

    private static NeoLib instance;
    private HookManager hookManager;

    @Override
    public void onLoad() {
        instance = this;

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();

        CommandAPI.onLoad(new CommandAPIPaperConfig(this).silentLogs(true));
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();

        CommandAPI.onEnable();

        SpigotEntityLibPlatform platform = new SpigotEntityLibPlatform(this);
        APIConfig entityConfig = new APIConfig(PacketEvents.getAPI()).usePlatformLogger();
        EntityLib.init(platform, entityConfig);

        hookManager = new HookManager(this);
        hookManager.register();

        getLogger().info("NeoLib v" + getDescription().getVersion() + " habilitado.");
        getLogger().info("Hooks: " + hookManager.getSummary());
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        if (PacketEvents.getAPI() != null) {
            PacketEvents.getAPI().terminate();
        }
        getLogger().info("NeoLib deshabilitado.");
    }

    public static NeoLib get() {
        return instance;
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public static DatabaseManager mysql(String host, int port, String database, String user, String password) {
        return DatabaseManager.mysql(host, port, database, user, password);
    }

    public static DatabaseManager sqlite(String path) {
        return DatabaseManager.sqlite(path);
    }

    public static DatabaseManager h2(String dataFolderPath) {
        return DatabaseManager.h2(dataFolderPath);
    }

    public static MongoManager mongo(String uri, String database) {
        return MongoManager.connect(uri, database);
    }

    public static RedisManager redis(String host, int port, String password, int db) {
        return RedisManager.connect(host, port, password, db);
    }

    /** One-liner DB setup: load DatabaseConfig, call this, get back a ready NeoDatabase. */
    public static NeoDatabase database(DatabaseConfig config, String dataFolderPath) {
        return NeoDatabase.connect(config, dataFolderPath);
    }

    /**
     * File-based DB setup — loads DatabaseConfig from the given file and returns a ready NeoDatabase.
     * Prefer this over database(DatabaseConfig, String) from dependent plugins to avoid classloader
     * conflicts during hot-reload (Paper remapper can't resolve DatabaseConfig across classloaders).
     *
     * Usage from Kotlin:
     *   db = NeoLib.database(File(dataFolder, "database.yml"), dataFolder.absolutePath)
     */
    public static NeoDatabase database(File configFile, String dataFolderPath) {
        DatabaseConfig config = NeoConfig.load(DatabaseConfig.class, configFile);
        return NeoDatabase.connect(config, dataFolderPath);
    }
}

