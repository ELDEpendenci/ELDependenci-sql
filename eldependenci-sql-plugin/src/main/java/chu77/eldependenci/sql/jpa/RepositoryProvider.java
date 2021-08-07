package chu77.eldependenci.sql.jpa;

import chu77.eldependenci.sql.manager.JpaFactoryManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryComposition;

import javax.inject.Inject;
import javax.inject.Provider;

public final class RepositoryProvider<T> implements Provider<T> {

    @Inject
    private JpaFactoryManager jpaFactoryService;

    @Inject
    private RepoImplementManager repoImplementManager;

    private final Class<T> jpaClass;

    public RepositoryProvider(Class<T> jpaClass) {
        this.jpaClass = jpaClass;
    }

    @Override
    public T get() {
        JpaRepositoryFactory factory = jpaFactoryService.getJpaFactory();
        var customImplements = repoImplementManager.getImplementations(jpaClass);
        if (customImplements.length > 0) {
            return factory.getRepository(jpaClass, RepositoryComposition.RepositoryFragments.just(customImplements));
        } else {
            return factory.getRepository(jpaClass);
        }
    }

}
