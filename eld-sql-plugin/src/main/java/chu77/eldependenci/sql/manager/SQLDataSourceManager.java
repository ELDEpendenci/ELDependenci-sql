package chu77.eldependenci.sql.manager;

import chu77.eldependenci.sql.SQLDataSource;
import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.datasource.ELDDataSource;
import org.sql2o.Sql2o;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;


public final class SQLDataSourceManager implements SQLDataSource {

    @Inject
    private Dbconfig db;

    private final ELDDataSource dataSource;

    @Inject
    public SQLDataSourceManager(Dbconfig db, Map<String, ELDDataSource> dbMap) {
        dataSource = Optional.ofNullable(dbMap.get(db.dataSource)).orElseThrow(() -> new IllegalStateException("未知的 DataSource: "+db.dataSource));
    }

    public void initialize() {
        dataSource.initialize(db);
    }

    @Override
    public Connection getConnection() throws SQLException {
        validate();
        return dataSource.getDataSource().getConnection();
    }

    @Override
    public DataSource getDataSource() {
        validate();
        return dataSource.getDataSource();
    }

    @Override
    public Sql2o getSql2o() {
        validate();
        return dataSource.getSql2o();
    }

    private void validate(){
        if (!db.enable) throw new IllegalStateException("SQL 已被禁用，請在 db.yml 啟用它");
    }
}
