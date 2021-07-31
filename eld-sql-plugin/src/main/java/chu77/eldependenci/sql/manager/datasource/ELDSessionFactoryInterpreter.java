package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.EntityRegistration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ELDSessionFactoryInterpreter implements ELDDataSourceFactory {

    protected final Map<String, SessionFactory> sessionFactoryMap = new ConcurrentHashMap<>();

    protected void loadEntityRegistration(Map<String, EntityRegistration> registrationMap, DataSource dataSource){
        registrationMap.forEach((k, v) -> {
            Configuration configuration = new Configuration();
            configuration.addProperties(generateDataSourceProperty(dataSource));
            v.getEntityClasses().forEach(configuration::addAnnotatedClass);
            sessionFactoryMap.put(k, configuration.buildSessionFactory());
        });
    }

    protected Properties generateDataSourceProperty(DataSource dataSource){
        Properties properties = new Properties();
        properties.put(Environment.CONNECTION_PROVIDER, DatasourceConnectionProviderImpl.class.getName());
        properties.put(Environment.DATASOURCE, dataSource);
        return properties;
    }


    @Override
    public SessionFactory getSessionFactory(String sessionName) {
        return sessionFactoryMap.get(sessionName);
    }
}
