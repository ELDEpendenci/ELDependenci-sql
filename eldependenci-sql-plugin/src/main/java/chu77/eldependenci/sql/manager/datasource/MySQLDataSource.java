package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.config.Dbconfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.MySQL8Dialect;

import javax.sql.DataSource;

public final class MySQLDataSource extends ELDSessionFactoryInterpreter {

    private DataSource dataSource;


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

        this.loadEntityRegistration(dataSource, MySQL8Dialect.class);

    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    /*
     hibernate.connection.provider_class = com.zaxxer.hikari.hibernate.HikariConnectionProvider
        hibernate.hikari.minimumIdle = 5
        hibernate.hikari.maximumPoolSize = 10
        hibernate.hikari.idleTimeout = 30000
        hibernate.hikari.dataSourceClassName = com.mysql.jdbc.jdbc2.optional.MysqlDataSource
        hibernate.hikari.dataSource.url = jdbc:mysql:
        //localhost/database
        hibernate.hikari.dataSource.user = bart
        hibernate.hikari.dataSource.password = 51 mp50n
     */
}
