package chu77.eldependenci.sql;

import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQL 服務
 */
public interface SQLService {

    /**
     * @return SQL連接
     * @throws SQLException SQL Error
     */
    Connection getConnection() throws SQLException;

    /**
     * @return 連接池
     */
    DataSource getDataSource();

    /**
     *
     * @return Hibernate 的 SessionFactory
     */
    SessionFactory getSessionFactory();

}
