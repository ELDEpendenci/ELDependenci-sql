package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.EntityRegistration;
import chu77.eldependenci.sql.SQLAddon;
import chu77.eldependenci.sql.config.Dbconfig;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.File;
import java.util.Map;

public final class SQLiteDataSource extends ELDSessionFactoryInterpreter {

    private DataSource dataSource;

    @Inject
    private SQLAddon plugin;

    @Inject
    private Map<String, EntityRegistration> jpaRegistrationMap;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }


    @Override
    public void initialize(Dbconfig dbconfig) {
        var path = new File(plugin.getDataFolder(), dbconfig.sqlite.file);
        org.sqlite.SQLiteDataSource dataSource = new org.sqlite.SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + path.getAbsolutePath());
        this.dataSource = dataSource;

        this.loadEntityRegistration(jpaRegistrationMap, dataSource);
    }

}
