package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.config.Dbconfig;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

public final class CustomDataSource extends ELDSessionFactoryInterpreter{

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource, Class<? extends Dialect> dialect) {
        this.dataSource = dataSource;
        this.loadEntityRegistration(this.dataSource, dialect);
    }

    @Override
    public DataSource getDataSource() {
        if (dataSource == null ){
            throw new IllegalStateException("Custom DataSource is not set");
        }
        return dataSource;
    }

    @Override
    public void initialize(Dbconfig dbconfig) {
    }
}
