package chu77.eldependenci.sql.jpa;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RepoImplementManager {

    private static final Object[] EMPTY = new Object[0];

    private final Map<Class<?>, Object[]> customImplements = new ConcurrentHashMap<>();

    @Inject
    public RepoImplementManager(Injector injector, @Named("jpa-custom-implements") Map<Class<?>, Class<?>[]> customImplements) {
        customImplements.forEach((repo, cls) -> {
            var objs = Arrays.stream(cls).map(injector::getInstance).toArray();
            this.customImplements.put(repo, objs);
        });
    }


    public Object[] getImplementations(Class<?> repo) {
        if (!customImplements.containsKey(repo)) return EMPTY;
        return customImplements.get(repo);
    }
}
