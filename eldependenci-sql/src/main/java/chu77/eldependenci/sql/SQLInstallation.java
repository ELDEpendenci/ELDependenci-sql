package chu77.eldependenci.sql;

import org.hibernate.dialect.Dialect;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.sql.DataSource;

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

    /**
     * 設置自定義的 DataSource (數據源)
     * @param dataSource 自定義的 DataSource
     * @param dialect 數據源的類型
     */
    void injectDataSource(DataSource dataSource, Class<? extends Dialect> dialect);

}
