package chu77.eldependenci.sql.manager;

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
    public SQLDataSourceManager(Map<String, ELDDataSourceFactory> dbMap, Dbconfig db){
        this.db = db;
        dataSourceFactory = Optional.ofNullable(dbMap.get(db.dataSource)).orElseThrow(() -> new IllegalStateException("未知的 DataSource: " + db.dataSource));
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
    public SessionFactory getSessionFactory(String sessionName) {
        return dataSourceFactory.getSessionFactory(sessionName);
    }

    private void validate() {
        if (!db.enable) throw new IllegalStateException("SQL 已被禁用，請在 db.yml 啟用它");
    }
}
