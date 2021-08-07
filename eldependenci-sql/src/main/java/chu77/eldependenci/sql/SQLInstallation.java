package chu77.eldependenci.sql;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SQL 安裝器
 */
public interface SQLInstallation {

    /**
     * 綁定 hibernate 的 entities
     * @param entities table class
     */
    void bindEntities(Class<?>... entities);

    /**
     * 綁定 spring data jpa repository 的 自定義實例類
     * @param repository jpa repository 類
     * @param customImplements 自定義實例類
     */
    void bindJpaRepository(Class<? extends JpaRepository<?,?>> repository, Class<?>... customImplements);

}
