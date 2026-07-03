package me.neondeex.neolib.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

    private final JedisPool pool;

    private RedisManager(JedisPool pool) {
        this.pool = pool;
    }

    public static RedisManager connect(String host, int port, String password, int db) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMinIdle(2);
        if (password != null && !password.isEmpty()) {
            return new RedisManager(new JedisPool(config, host, port, 2000, password, db));
        }
        return new RedisManager(new JedisPool(config, host, port, 2000, null, db));
    }

    public Jedis getResource() {
        return pool.getResource();
    }

    public boolean isRunning() {
        return pool != null && !pool.isClosed();
    }

    public void close() {
        if (isRunning()) pool.close();
    }
}
