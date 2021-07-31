package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.EntityRegistration;
import chu77.eldependenci.sql.SQLAddon;
import org.hibernate.SessionFactory;
import org.hibernate.boot.cfgxml.spi.LoadedConfig;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ELDSessionFactoryInterpreter implements ELDDataSourceFactory {

    @Inject
    private Map<String, EntityRegistration> jpaRegistrationMap;

    @Inject
    private SQLAddon sqlAddon;

    protected final Map<String, SessionFactory> sessionFactoryMap = new ConcurrentHashMap<>();

    protected void loadEntityRegistration(DataSource dataSource, Class<? extends Dialect> dialect) {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, dialect.getName());
        properties.put(Environment.CONNECTION_PROVIDER, DatasourceConnectionProviderImpl.class.getName());
        properties.put(Environment.DATASOURCE, dataSource);
        File propsFile = new File(sqlAddon.getDataFolder(), "hibernate.properties");
        try(FileInputStream is = new FileInputStream(propsFile)){
            properties.load(is);
        }catch (IOException e){
            sqlAddon.getLogger().warning("Error while loading hibernate.properties: "+e.getMessage());
        }
        jpaRegistrationMap.forEach((k, v) -> {
            Configuration configuration = new Configuration();
            configuration.addProperties(properties);
            v.getEntityClasses().forEach(configuration::addAnnotatedClass);
            sessionFactoryMap.put(k, configuration.buildSessionFactory());
        });
    }


    @Override
    public SessionFactory getSessionFactory(String sessionName) {
        return sessionFactoryMap.get(sessionName);
    }
}
