package chu77.eldependenci.sql.manager.datasource;

import chu77.eldependenci.sql.config.Dbconfig;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

public interface ELDDataSource {

    DataSource getDataSource();

    Sql2o getSql2o();

    void initialize(Dbconfig dbconfig);

}
