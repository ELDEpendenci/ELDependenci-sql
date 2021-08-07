package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.config.Dbconfig;
import com.enigmabridge.hibernate.dialect.SQLiteDialect;

import javax.sql.DataSource;
import java.io.File;

public final class SQLiteDataSource extends ELDSessionFactoryInterpreter {

    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }


    @Override
    public void initialize(Dbconfig dbconfig) {
        var path = new File(sqlAddon.getDataFolder(), dbconfig.sqlite.file);
        org.sqlite.SQLiteDataSource dataSource = new org.sqlite.SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + path.getAbsolutePath());
        this.dataSource = dataSource;
        this.loadEntityRegistration(dataSource, SQLiteDialect.class);
    }

}
