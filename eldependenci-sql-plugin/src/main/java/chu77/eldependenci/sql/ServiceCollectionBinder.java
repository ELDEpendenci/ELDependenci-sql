package chu77.eldependenci.sql;

import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.JpaFactoryManager;
import chu77.eldependenci.sql.manager.SQLDataSourceManager;
import chu77.eldependenci.sql.manager.datasource.CustomDataSource;
import chu77.eldependenci.sql.manager.datasource.ELDDataSourceFactory;
import chu77.eldependenci.sql.manager.datasource.MySQLDataSource;
import chu77.eldependenci.sql.manager.datasource.SQLiteDataSource;
import com.ericlam.mc.eld.ServiceCollection;

import java.util.Map;

public class ServiceCollectionBinder {

    public static void bind(ServiceCollection serviceCollection){
        serviceCollection.addConfiguration(Dbconfig.class);

        serviceCollection.addServices(ELDDataSourceFactory.class, Map.of(
                "mysql", MySQLDataSource.class,
                "sqlite", SQLiteDataSource.class,
                "custom", CustomDataSource.class
        ));

        serviceCollection.bindService(SQLService.class, SQLDataSourceManager.class);
        serviceCollection.addSingleton(JpaFactoryManager.class);
    }
}
