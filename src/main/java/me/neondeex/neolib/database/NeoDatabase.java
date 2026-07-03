package me.neondeex.neolib.database;

/**
 * Unified database handle. Create via {@link NeoDatabase#connect(DatabaseConfig, String)}.
 * Owns the lifecycle of the underlying connection/pool — call close() in onDisable().
 *
 * Usage:
 *   val db = NeoDatabase.connect(dbConfig, dataFolder.absolutePath)
 *   // SQL  -> db.sql().getConnection().use { ... }
 *   // Mongo -> db.mongo().collection("players").find(...)
 *   // Redis -> db.redis().getResource().use { jedis -> ... }
 */
public class NeoDatabase implements AutoCloseable {

    private final DatabaseType type;
    private final DatabaseManager sql;
    private final MongoManager    mongo;
    private final RedisManager    redis;

    private NeoDatabase(DatabaseType type, DatabaseManager sql, MongoManager mongo, RedisManager redis) {
        this.type  = type;
        this.sql   = sql;
        this.mongo = mongo;
        this.redis = redis;
    }

    public static NeoDatabase connect(DatabaseConfig config, String dataFolderPath) {
        switch (config.type) {
            case MYSQL:
                return new NeoDatabase(DatabaseType.MYSQL,
                    DatabaseManager.mysql(config.host, config.port, config.database, config.username, config.password),
                    null, null);
            case H2:
                return new NeoDatabase(DatabaseType.H2, DatabaseManager.h2(dataFolderPath), null, null);
            case SQLITE:
                return new NeoDatabase(DatabaseType.SQLITE,
                    DatabaseManager.sqlite(dataFolderPath + "/database.db"), null, null);
            case MONGODB:
                return new NeoDatabase(DatabaseType.MONGODB, null,
                    MongoManager.connect(config.mongoUri, config.mongoDatabase), null);
            case REDIS:
                return new NeoDatabase(DatabaseType.REDIS, null, null,
                    RedisManager.connect(config.redisHost, config.redisPort, config.redisPassword, config.redisDatabase));
            case FLAT:
            default:
                return new NeoDatabase(DatabaseType.FLAT, null, null, null);
        }
    }

    public DatabaseType getType() { return type; }

    public boolean isSql()   { return sql   != null; }
    public boolean isMongo() { return mongo != null; }
    public boolean isRedis() { return redis != null; }

    public DatabaseManager sql() {
        if (sql == null) throw new IllegalStateException("Not a SQL backend (type=" + type + ")");
        return sql;
    }

    public MongoManager mongo() {
        if (mongo == null) throw new IllegalStateException("Not a MongoDB backend (type=" + type + ")");
        return mongo;
    }

    public RedisManager redis() {
        if (redis == null) throw new IllegalStateException("Not a Redis backend (type=" + type + ")");
        return redis;
    }

    @Override
    public void close() {
        if (sql   != null) sql.close();
        if (mongo != null) mongo.close();
        if (redis != null) redis.close();
    }
}
