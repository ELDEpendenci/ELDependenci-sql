package chu77.eldependenci.sql.manager;

import chu77.eldependenci.sql.bukkit.SQLAddon;
import chu77.eldependenci.sql.SQLService;
import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.datasource.ELDDataSourceFactory;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;


public final class SQLDataSourceManager implements SQLService {

    private final Dbconfig db;
    private final ELDDataSourceFactory dataSourceFactory;

    @Inject
    public SQLDataSourceManager(Map<String, ELDDataSourceFactory> dbMap, Dbconfig db, SQLAddon sqlAddon) {
        this.db = db;
        dataSourceFactory = Optional.ofNullable(dbMap.get(db.dataSource)).orElseThrow(() -> new IllegalStateException("Unknown DataSource: " + db.dataSource));
        if (db.enable) {
            sqlAddon.getLogger().info("Initializing SQL....");
            this.initialize();
        }
    }

    public void initialize() {
        dataSourceFactory.initialize(db);
    }

    @Override
    public Connection getConnection() throws SQLException {
        validate();
        return dataSourceFactory.getDataSource().getConnection();
    }

    @Override
    public DataSource getDataSource() {
        validate();
        return dataSourceFactory.getDataSource();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return dataSourceFactory.getSessionFactory();
    }

    private void validate() {
        if (!db.enable) throw new IllegalStateException("SQL is disabled, please enable it in db.yml");
    }
}
