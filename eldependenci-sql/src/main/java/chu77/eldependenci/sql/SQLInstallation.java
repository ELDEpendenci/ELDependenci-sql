package chu77.eldependenci.sql;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SQL 安裝器
 */
public interface SQLInstallation {

    /**
     * 綁定 spring data jpa repository
     * @param entity table class
     * @param repo jpa repository class
     * @param <T> table 類
     */
    <T> void bindRepository(Class<T> entity, Class<? extends JpaRepository<T , ?>> repo);

    /**
     * 綁定 spring data jpa repository 的 自定義實例類
     * @param repository jpa repository 類
     * @param customImplements 自定義實例類
     */
    void bindCustomImplements(Class<? extends JpaRepository<?,?>> repository, Class<?>... customImplements);

}
