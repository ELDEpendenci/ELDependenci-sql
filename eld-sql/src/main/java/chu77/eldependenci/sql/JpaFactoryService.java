package chu77.eldependenci.sql;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

/**
 * Jpa Repository 的工廠
 */
public interface JpaFactoryService {

    /**
     * 獲取 該 session 的 所有 entity 的工廠
     * @param session 註冊名稱
     * @return JpaRepository 工廠
     */
    JpaRepositoryFactory getJpaFactory(String session);

}
