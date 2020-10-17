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
    private static DataSource sqlsource;

    private  Sql2o sql2o;

    @Inject
    private Dbconfig db;
    private SQLDataSourceManager(){
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
        boolean enable = db.enable;
        long connectionTimeout = db.pool.connectionTimeout;
        long idleTimeout = db.pool.idleTimeout;
        long maxLifeTime = db.pool.maxLifeTime;
        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + database + "?" + "useSSL=" + SSL;
        if(enable){
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
            sqlsource = new HikariDataSource(config);
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        return sqlsource.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return sqlsource;
    }
    @Override
    public Sql2o getSql2o() throws SQLException {
        return sql2o;
    }
}
