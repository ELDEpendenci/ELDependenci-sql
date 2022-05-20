package chu77.eldependenci.sql.bukkit;


import chu77.eldependenci.sql.ServiceCollectionBinder;
import com.ericlam.mc.eld.BukkitManagerProvider;
import com.ericlam.mc.eld.ELDBukkit;
import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ServiceCollection;

@ELDBukkit(
        lifeCycle = SQLAddonLifecycle.class,
        registry = SQLAddonRegistry.class
)
public class SQLAddon extends ELDBukkitPlugin {

    @Override
    public void bindServices(ServiceCollection serviceCollection) {
        ServiceCollectionBinder.bind(serviceCollection);
    }


    @Override
    protected void manageProvider(BukkitManagerProvider bukkitManagerProvider) {

    }
}


