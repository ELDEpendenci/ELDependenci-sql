package chu77.eldependenci.sql.manager;

import chu77.eldependenci.sql.JpaFactoryService;
import chu77.eldependenci.sql.SQLService;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class JpaFactoryManager implements JpaFactoryService {

    @Inject
    private SQLService sqlService;

    private final Map<String, JpaRepositoryFactory> factoryMap = new ConcurrentHashMap<>();

    @Override
    public JpaRepositoryFactory getJpaFactory(String session) {
        if (factoryMap.containsKey(session)) return factoryMap.get(session);
        EntityManagerFactory emf = sqlService.getSessionFactory(session);
        if (emf == null) throw new IllegalStateException("cannot get JpaFactory from session name: "+session);
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(emf);
        jpaTransactionManager.setDataSource(sqlService.getDataSource());
        final JpaRepositoryFactory factory = new JpaRepositoryFactory(emf.createEntityManager());
        factory.addRepositoryProxyPostProcessor((factory1, repositoryInformation) -> factory1.addAdvice(new TransactionInterceptor((TransactionManager) jpaTransactionManager, new AnnotationTransactionAttributeSource())));
        this.factoryMap.put(session, factory);
        return factory;
    }


}
