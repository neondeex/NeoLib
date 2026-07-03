package me.neondeex.neolib.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private final HikariDataSource dataSource;

    private DatabaseManager(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DatabaseManager mysql(String host, int port, String database, String user, String password) {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true&characterEncoding=utf8");
        cfg.setUsername(user);
        cfg.setPassword(password);
        cfg.setMaximumPoolSize(10);
        cfg.setMinimumIdle(2);
        cfg.setConnectionTimeout(30_000);
        cfg.setIdleTimeout(600_000);
        cfg.setMaxLifetime(1_800_000);
        cfg.setPoolName("NeoLib-MySQL");
        cfg.addDataSourceProperty("cachePrepStmts", "true");
        cfg.addDataSourceProperty("prepStmtCacheSize", "250");
        cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new DatabaseManager(new HikariDataSource(cfg));
    }

    public static DatabaseManager sqlite(String filePath) {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:sqlite:" + filePath);
        cfg.setDriverClassName("org.sqlite.JDBC");
        cfg.setMaximumPoolSize(1);
        cfg.setPoolName("NeoLib-SQLite");
        return new DatabaseManager(new HikariDataSource(cfg));
    }

    public static DatabaseManager h2(String dataFolderPath) {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:h2:" + dataFolderPath.replace("\\", "/") + "/database;MODE=MySQL;DATABASE_TO_UPPER=false");
        cfg.setDriverClassName("org.h2.Driver");
        cfg.setMaximumPoolSize(1);
        cfg.setPoolName("NeoLib-H2");
        return new DatabaseManager(new HikariDataSource(cfg));
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public boolean isRunning() {
        return dataSource != null && !dataSource.isClosed();
    }

    public void close() {
        if (isRunning()) {
            dataSource.close();
        }
    }
}
