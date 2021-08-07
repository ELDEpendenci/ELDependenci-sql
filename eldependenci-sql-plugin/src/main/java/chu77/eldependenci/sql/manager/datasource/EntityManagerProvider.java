package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.SQLService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class EntityManagerProvider implements Provider<EntityManager> {

    private final SessionFactory sessionFactory;

    @Inject
    public EntityManagerProvider(SQLService sqlService) {
        this.sessionFactory = sqlService.getSessionFactory();
    }

    @Override
    public EntityManager get() {
        return sessionFactory.createEntityManager();
    }
}
