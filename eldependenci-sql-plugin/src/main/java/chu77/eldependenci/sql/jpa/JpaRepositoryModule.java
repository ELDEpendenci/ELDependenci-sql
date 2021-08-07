package chu77.eldependenci.sql.jpa;

import chu77.eldependenci.sql.ELDSQLInstallation;
import chu77.eldependenci.sql.manager.datasource.EntityManagerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Set;

public final class JpaRepositoryModule extends AbstractModule {

    private final ELDSQLInstallation eldsqlInstallation;

    public JpaRepositoryModule(ELDSQLInstallation eldsqlInstallation) {
        this.eldsqlInstallation = eldsqlInstallation;
    }

    @Override
    protected void configure() {

        bind(EntityManager.class).toProvider(EntityManagerProvider.class).in(Scopes.SINGLETON);
        eldsqlInstallation.getRepositories().forEach(re -> bind(re).toProvider(new RepositoryProvider<>(re)));
        bind(new TypeLiteral<Set<Class<?>>>(){}).annotatedWith(Names.named("jpa-entities")).toInstance(eldsqlInstallation.getEntities());
        bind(new TypeLiteral<Map<Class<?>, Class<?>[]>>(){}).annotatedWith(Names.named("jpa-custom-implements")).toInstance(eldsqlInstallation.getCustomImplements());
        bind(RepoImplementManager.class).in(Scopes.SINGLETON);

        /*
        JpaTransactionInterceptor transactionInterceptor = new JpaTransactionInterceptor();
        requestInjection(transactionInterceptor);
        bindInterceptor(Matchers.annotatedWith(Transactional.class), Matchers.any(), transactionInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor);
         */

        /*
        RepositoryTypeListener repositoryTypeListener = new RepositoryTypeListener();
        requestInjection(repositoryTypeListener);
        bindListener(Matchers.any(), repositoryTypeListener);

         */
    }
}
