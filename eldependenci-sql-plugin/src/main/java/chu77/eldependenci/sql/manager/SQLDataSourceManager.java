package chu77.eldependenci.sql.manager;

import chu77.eldependenci.sql.SQLService;
import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.datasource.ELDDataSourceFactory;
import com.ericlam.mc.eld.MCPlugin;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;


public final class SQLDataSourceManager implements SQLService {

    private final Dbconfig db;
    private final ELDDataSourceFactory dataSourceFactory;

    @Inject
    public SQLDataSourceManager(Map<String, ELDDataSourceFactory> dbMap, Dbconfig db, @Named("sql-addon") MCPlugin sqlAddon) {
        this.db = db;
        dataSourceFactory = Optional.ofNullable(dbMap.get(db.dataSource)).orElseThrow(() -> new IllegalStateException("Unknown DataSource: " + db.dataSource));
        sqlAddon.getLogger().info("Initializing SQL... (Using DataSource: " + db.dataSource + ")");
        this.initialize();
    }

    public void initialize() {
        dataSourceFactory.initialize(db);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSourceFactory.getDataSource().getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return dataSourceFactory.getDataSource();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return dataSourceFactory.getSessionFactory();
    }
}
