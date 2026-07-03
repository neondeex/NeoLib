package me.neondeex.neolib.database;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Header("Database Configuration — shared NeoLib config")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class DatabaseConfig extends OkaeriConfig {

    @Comment("Storage backend: FLAT, MYSQL, H2, SQLITE, MONGODB, REDIS")
    public DatabaseType type = DatabaseType.FLAT;

    @Comment("SQL connection settings — used for MYSQL, H2, and SQLITE")
    public String host     = "localhost";
    public int    port     = 3306;
    public String database = "plugin_db";
    public String username = "root";
    public String password = "";

    @Comment("MongoDB URI — used when type=MONGODB")
    public String mongoUri      = "mongodb://localhost:27017";
    public String mongoDatabase = "plugin_db";

    @Comment("Redis settings — used when type=REDIS")
    public String redisHost     = "localhost";
    public int    redisPort     = 6379;
    public String redisPassword = "";
    public int    redisDatabase = 0;
}
