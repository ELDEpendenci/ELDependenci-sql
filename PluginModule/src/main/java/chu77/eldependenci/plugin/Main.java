package chu77.eldependenci.plugin;


import chu77.eldependenci.plugin.configmanager.Dbconfig;
import chu77.eldependenci.plugin.sqlmanager.SQLDataSourceManager;
import chu77.eldependenci.sql.SQLDataSource;
import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ManagerProvider;
import com.ericlam.mc.eld.ServiceCollection;
import com.ericlam.mc.eld.annotations.ELDPlugin;

@ELDPlugin(
        lifeCycle = Lifecycle.class,
        registry = Myregistry.class
)
public class Main extends ELDBukkitPlugin {

    @Override
    protected void bindServices(ServiceCollection serviceCollection) {
        serviceCollection.addConfiguration(Dbconfig.class);
        serviceCollection.bindService(SQLDataSource.class, SQLDataSourceManager.class);

    }

    @Override
    protected void manageProvider(ManagerProvider managerProvider) {

    }
}


