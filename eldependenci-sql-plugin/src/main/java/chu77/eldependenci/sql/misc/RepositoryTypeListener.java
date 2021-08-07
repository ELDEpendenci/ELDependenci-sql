package chu77.eldependenci.sql.misc;

import chu77.eldependenci.sql.manager.JpaFactoryManager;
import com.google.inject.Inject;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import java.lang.reflect.Field;

@Deprecated
public class RepositoryTypeListener implements TypeListener {

    @Inject
    private JpaFactoryManager factory;

    @Override
    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        Class<?> clazz = typeLiteral.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (JpaRepository.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Autowired.class)) {
                    typeEncounter.register(new RepositoryMemberInjector<>(field, factory));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private static class RepositoryMemberInjector<T> implements MembersInjector<T> {

        private final JpaRepositoryFactory repositoryFactory;
        private final Field field;

        private RepositoryMemberInjector(Field field, JpaFactoryManager factoryService) {
            field.setAccessible(true);
            this.field = field;
            this.repositoryFactory = factoryService.getJpaFactory();
        }

        @Override
        public void injectMembers(T t) {
            try {
                field.set(t, repositoryFactory.getRepository(field.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
