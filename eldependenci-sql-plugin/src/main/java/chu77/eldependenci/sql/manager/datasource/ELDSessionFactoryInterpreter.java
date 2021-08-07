package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.SQLAddon;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public abstract class ELDSessionFactoryInterpreter implements ELDDataSourceFactory {

    @Named("jpa-entities")
    @Inject
    private Set<Class<?>> jpaEntities;

    @Inject
    protected SQLAddon sqlAddon;

    protected SessionFactory sessionFactory;
    private Properties properties;

    protected void loadEntityRegistration(DataSource dataSource, Class<? extends Dialect> dialect) {
        this.properties = getProperties(dataSource, dialect);
        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        jpaEntities.forEach(configuration::addAnnotatedClass);
        this.sessionFactory = configuration.buildSessionFactory();
    }


    public Properties getProperties(DataSource dataSource, Class<? extends Dialect> dialect) {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, dialect.getName());
        properties.put(Environment.CONNECTION_PROVIDER, DatasourceConnectionProviderImpl.class.getName());
        properties.put(Environment.DATASOURCE, dataSource);
        File propsFile = new File(sqlAddon.getDataFolder(), "hibernate.properties");
        try (FileInputStream is = new FileInputStream(propsFile)) {
            properties.load(is);
        } catch (IOException e) {
            sqlAddon.getLogger().warning("Error while loading hibernate.properties: " + e.getMessage());
        }
        return properties;
    }


    @Override
    public SessionFactory getSessionFactory() {
        return Optional.ofNullable(sessionFactory).orElseThrow(() -> new IllegalStateException("sessionFactory is not loaded"));
    }


    @Override
    public Properties getHibernateProperties() {
        return Optional.ofNullable(properties).orElseThrow(() -> new IllegalStateException("hibernate properties is not loaded"));
    }
}
