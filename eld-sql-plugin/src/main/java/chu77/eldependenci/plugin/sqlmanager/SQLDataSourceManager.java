package chu77.eldependenci.plugin.sqlmanager;

import chu77.eldependenci.plugin.configmanager.Dbconfig;
import chu77.eldependenci.sql.SQLDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class SQLDataSourceManager implements SQLDataSource {

    private static DataSource dataSource;

    @Inject
    private Dbconfig db;

    private Sql2o sql2o;

    public void initialize(){
        if (!db.enable) throw new IllegalStateException("SQL 已被禁用，請在 db.yml 啟用它");
        HikariConfig config = new HikariConfig();
        String host = db.host;
        String port = db.port + "";
        String database = db.database;
        String username = db.username;
        String password = db.password;
        String poolname = db.pool.name;
        int minsize = db.pool.minSize;
        int maxsize = db.pool.maxSize;
        boolean SSL = db.useSSL;
        long connectionTimeout = db.pool.connectionTimeout;
        long idleTimeout = db.pool.idleTimeout;
        long maxLifeTime = db.pool.maxLifeTime;
        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + database + "?" + "useSSL=" + SSL;
        config.setJdbcUrl(jdbc);
        config.setPoolName(poolname);
        config.setMaximumPoolSize(maxsize);
        config.setMinimumIdle(minsize);
        config.setUsername(username);
        config.setPassword(password);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifeTime);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("characterEncoding", "utf8");
        dataSource = new HikariDataSource(config);
        sql2o = new Sql2o(dataSource);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (!db.enable) throw new IllegalStateException("SQL 已被禁用，請在 db.yml 啟用它");
        return dataSource.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        if (!db.enable) throw new IllegalStateException("SQL 已被禁用，請在 db.yml 啟用它");
        return dataSource;
    }

    @Override
    public Sql2o getSql2o(){
        if (!db.enable) throw new IllegalStateException("SQL 已被禁用，請在 db.yml 啟用它");
        return sql2o;
    }
}
