package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.config.Dbconfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

public final class MySQLDataSource implements ELDDataSource {

    private DataSource dataSource;
    private Sql2o sql2o;

    @Override
    public void initialize(Dbconfig dbconfig) {
        var db = dbconfig.mysql;
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
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Sql2o getSql2o() {
        return sql2o;
    }
}
