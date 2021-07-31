package chu77.eldependenci.sql;

import java.util.List;

/**
 * 註冊 Hibernate 的 SQL Entities
 */
public interface EntityRegistration {

    /**
     * @return 所有即將註冊的 entities
     */
    List<Class<?>> getEntityClasses();

}
