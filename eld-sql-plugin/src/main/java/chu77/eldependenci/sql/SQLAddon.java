package chu77.eldependenci.sql;


import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.JpaFactoryManager;
import chu77.eldependenci.sql.manager.SQLDataSourceManager;
import chu77.eldependenci.sql.manager.datasource.ELDDataSourceFactory;
import chu77.eldependenci.sql.manager.datasource.MySQLDataSourceFactory;
import chu77.eldependenci.sql.manager.datasource.SQLiteDataSource;
import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ManagerProvider;
import com.ericlam.mc.eld.ServiceCollection;
import com.ericlam.mc.eld.annotations.ELDPlugin;

import java.io.File;
import java.util.Map;

@ELDPlugin(
        lifeCycle = SQLAddonLifecycle.class,
        registry = SQLAddonRegistry.class
)
public class SQLAddon extends ELDBukkitPlugin {

    @Override
    protected void bindServices(ServiceCollection serviceCollection) {
        serviceCollection.addConfiguration(Dbconfig.class);
        serviceCollection.addServices(ELDDataSourceFactory.class, Map.of(
                "mysql", MySQLDataSourceFactory.class,
                "sqlite", SQLiteDataSource.class
        ));
        serviceCollection.bindService(SQLService.class, SQLDataSourceManager.class);
        serviceCollection.bindService(JpaFactoryService.class, JpaFactoryManager.class);
        serviceCollection.addServices(EntityRegistration.class, Map.of());

    }

    @Override
    protected void manageProvider(ManagerProvider managerProvider) {
        File prop = new File(getDataFolder(), "hibernate.properties");
        if (!prop.exists()) saveResource("hibernate.properties", true);
    }
}


