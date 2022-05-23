package chu77.eldependenci.sql.jpa;

import chu77.eldependenci.sql.ELDSQLInstallation;
import chu77.eldependenci.sql.manager.datasource.EntityManagerProvider;
import com.ericlam.mc.eld.MCPlugin;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Set;

public final class JpaRepositoryModule extends AbstractModule {

    private final ELDSQLInstallation eldsqlInstallation;
    private final MCPlugin plugin;

    public JpaRepositoryModule(ELDSQLInstallation eldsqlInstallation, MCPlugin plugin) {
        this.eldsqlInstallation = eldsqlInstallation;
        this.plugin = plugin;
    }

    @Override
    protected void configure() {

        bind(EntityManager.class).toProvider(EntityManagerProvider.class).in(Scopes.SINGLETON);
        eldsqlInstallation.getRepositories().forEach(re -> bind(re).toProvider(new RepositoryProvider<>(re)));
        bind(new TypeLiteral<Set<Class<?>>>(){}).annotatedWith(Names.named("jpa-entities")).toInstance(eldsqlInstallation.getEntitySet());
        bind(new TypeLiteral<Map<Class<?>, Class<?>[]>>(){}).annotatedWith(Names.named("jpa-custom-implements")).toInstance(eldsqlInstallation.getCustomImplements());
        bind(MCPlugin.class).annotatedWith(Names.named("sql-addon")).toInstance(plugin);
        bind(RepoImplementManager.class).in(Scopes.SINGLETON);
        bind(ELDSQLInstallation.CustomSource.class).toInstance(eldsqlInstallation.getCustomSource());

        /* unused

        JpaTransactionInterceptor transactionInterceptor = new JpaTransactionInterceptor();
        requestInjection(transactionInterceptor);
        bindInterceptor(Matchers.annotatedWith(Transactional.class), Matchers.any(), transactionInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), transactionInterceptor);
         */

        /* unused

        RepositoryTypeListener repositoryTypeListener = new RepositoryTypeListener();
        requestInjection(repositoryTypeListener);
        bindListener(Matchers.any(), repositoryTypeListener);

         */
    }

}
