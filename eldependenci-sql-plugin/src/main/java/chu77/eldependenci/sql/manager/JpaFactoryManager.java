package chu77.eldependenci.sql.manager;

import chu77.eldependenci.sql.SQLService;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public final class JpaFactoryManager {

    private final JpaRepositoryFactory jpaRepositoryFactory;

    @Inject
    public JpaFactoryManager(SQLService sqlService) {
        EntityManagerFactory emf = sqlService.getSessionFactory();
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(emf);
        EntityManager em = SharedEntityManagerCreator.createSharedEntityManager(emf);
        jpaTransactionManager.setDataSource(sqlService.getDataSource());
        final JpaRepositoryFactory factory = new JpaRepositoryFactory(em);
        factory.setBeanClassLoader(this.getClass().getClassLoader());
        factory.addRepositoryProxyPostProcessor((proxy, repositoryInformation) ->
                proxy.addAdvice(new TransactionInterceptor((TransactionManager) jpaTransactionManager, new AnnotationTransactionAttributeSource()))
        );
        this.jpaRepositoryFactory = factory;
    }

    public JpaRepositoryFactory getJpaFactory() {
        return jpaRepositoryFactory;
    }


}
