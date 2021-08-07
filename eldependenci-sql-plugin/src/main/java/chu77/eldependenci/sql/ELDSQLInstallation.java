package chu77.eldependenci.sql;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ELDSQLInstallation implements SQLInstallation {

    private final Map<Class<?>, Class<?>> repositoryMap = new HashMap<>();
    private final Map<Class<?>, Class<?>[]> customImplements = new HashMap<>();

    @Override
    public <T> void bindRepository(Class<T> entity, Class<? extends JpaRepository<T, ?>> repo) {
        this.repositoryMap.put(entity, repo);
    }

    @Override
    public void bindCustomImplements(Class<? extends JpaRepository<?, ?>> repository, Class<?>... customImplements) {
        this.customImplements.put(repository, customImplements);
    }


    public <T> Set<Class<T>> getRepositories() {
        return ImmutableSet.copyOf(repositoryMap.values().stream().map(r -> (Class<T>) r).collect(Collectors.toSet()));
    }

    public Set<Class<?>> getEntities(){
        return ImmutableSet.copyOf(repositoryMap.keySet());
    }


    public Map<Class<?>, Class<?>[]> getCustomImplements() {
        return ImmutableMap.copyOf(customImplements);
    }
}
