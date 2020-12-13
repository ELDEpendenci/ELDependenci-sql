package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.SQLAddon;
import chu77.eldependenci.sql.config.Dbconfig;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.io.File;

public final class SQLiteDataSource implements ELDDataSource {

    private DataSource dataSource;
    private Sql2o sql2o;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Sql2o getSql2o() {
        return sql2o;
    }

    @Override
    public void initialize(Dbconfig dbconfig) {
        var path = new File(SQLAddon.getProvidingPlugin(SQLAddon.class).getDataFolder(), dbconfig.sqlite.file);
        org.sqlite.SQLiteDataSource dataSource = new org.sqlite.SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + path.getAbsolutePath());
        this.dataSource = dataSource;
        sql2o = new Sql2o(dataSource);
    }
}
