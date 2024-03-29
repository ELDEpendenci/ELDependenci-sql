package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.config.Dbconfig;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.util.Properties;

public interface ELDDataSourceFactory {

    DataSource getDataSource();

    void initialize(Dbconfig dbconfig);

    SessionFactory getSessionFactory();

    Properties getHibernateProperties();

}
