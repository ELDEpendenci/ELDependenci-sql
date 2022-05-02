package chu77.eldependenci.sql;

import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

public interface DataSourceInjector {

    void injectExternal(DataSource dataSource, Class<? extends Dialect> dialect);

}
