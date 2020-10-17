package chu77.eldependenci.sql;

import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQL 服務
 */
public interface SQLDataSource {

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
     * @return  sql2o 方法
     */
    Sql2o getSql2o();
}
