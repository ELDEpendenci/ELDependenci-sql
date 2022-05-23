package chu77.eldependenci.sql.bungee;

import chu77.eldependenci.sql.ServiceCollectionBinder;
import com.ericlam.mc.eld.BungeeManageProvider;
import com.ericlam.mc.eld.ELDBungee;
import com.ericlam.mc.eld.ELDBungeePlugin;
import com.ericlam.mc.eld.ServiceCollection;

import java.io.File;

@ELDBungee(
        registry = SQLAddonRegistry.class,
        lifeCycle = SQLAddonLifecycle.class
)
public class SQLAddon extends ELDBungeePlugin {

    @Override
    public void bindServices(ServiceCollection serviceCollection) {
        ServiceCollectionBinder.bind(serviceCollection, this);
    }

    @Override
    protected void manageProvider(BungeeManageProvider bungeeManageProvider) {
        File file = new File(getDataFolder(), "hibernate.properties");
        if (!file.exists()) saveResource("hibernate.properties");
    }


}
