package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.ELDSQLInstallation;
import chu77.eldependenci.sql.config.Dbconfig;
import org.hibernate.dialect.Dialect;

import javax.inject.Inject;
import javax.sql.DataSource;

public final class CustomDataSource extends ELDSessionFactoryInterpreter{

    private DataSource dataSource;

    @Inject
    private ELDSQLInstallation.CustomSource customSource;

    @Override
    public DataSource getDataSource() {
        if (dataSource == null){
            throw new IllegalStateException("Custom DataSource is not set");
        }
        return dataSource;
    }

    @Override
    public void initialize(Dbconfig dbconfig) {
        if (customSource.notInstalled()){
            throw new IllegalStateException("using custom as data source but no custom data source was injected.");
        }
        this.dataSource = customSource.dataSource;
        this.loadEntityRegistration(this.dataSource, customSource.dialect);
    }
}
