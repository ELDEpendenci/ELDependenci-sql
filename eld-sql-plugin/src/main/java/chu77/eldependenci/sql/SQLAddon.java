package chu77.eldependenci.sql;


import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.SQLDataSourceManager;
import chu77.eldependenci.sql.manager.datasource.ELDDataSource;
import chu77.eldependenci.sql.manager.datasource.MySQLDataSource;
import chu77.eldependenci.sql.manager.datasource.SQLiteDataSource;
import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ManagerProvider;
import com.ericlam.mc.eld.ServiceCollection;
import com.ericlam.mc.eld.annotations.ELDPlugin;

import java.util.Map;

@ELDPlugin(
        lifeCycle = SQLAddonLifecycle.class,
        registry = SQLAddonRegistry.class
)
public class SQLAddon extends ELDBukkitPlugin {

    @Override
    protected void bindServices(ServiceCollection serviceCollection) {
        serviceCollection.addConfiguration(Dbconfig.class);
        serviceCollection.addServices(ELDDataSource.class, Map.of(
                "mysql", MySQLDataSource.class,
                "sqlite", SQLiteDataSource.class
        ));
        serviceCollection.bindService(SQLDataSource.class, SQLDataSourceManager.class);

    }

    @Override
    protected void manageProvider(ManagerProvider managerProvider) {

    }
}


