package chu77.eldependenci.sql;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.hibernate.dialect.Dialect;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

public final class ELDSQLInstallation implements SQLInstallation {

    private final Set<Class<?>> entitySet = new HashSet<>();
    private final Map<Class<?>, Class<?>[]> customImplements = new HashMap<>();

    private final CustomSource customSource = new CustomSource();

    @Override
    public void bindEntities(Class<?>... entities) {
        this.entitySet.addAll(Arrays.asList(entities));
    }

    @Override
    public void bindJpaRepository(Class<? extends JpaRepository<?, ?>> repository, Class<?>... customImplements) {
        this.customImplements.put(repository, customImplements);
    }

    @Override
    public void injectDataSource(DataSource dataSource, Class<? extends Dialect> dialect) {
       this.customSource.dataSource = dataSource;
       this.customSource.dialect = dialect;
    }

    public Set<Class<?>> getEntitySet() {
        return ImmutableSet.copyOf(entitySet);
    }

    @SuppressWarnings("unchecked")
    public <T> Set<Class<T>> getRepositories(){
        return customImplements.keySet().stream().map(k -> (Class<T>)k).collect(Collectors.toSet());
    }

    public Map<Class<?>, Class<?>[]> getCustomImplements() {
        return ImmutableMap.copyOf(customImplements);
    }


    public CustomSource getCustomSource() {
        return customSource;
    }

    public static class CustomSource {
        @Nullable
        public DataSource dataSource;
        @Nullable
        public Class<? extends Dialect> dialect;

        public boolean notInstalled(){
            return dataSource == null || dialect == null;
        }
    }

}
