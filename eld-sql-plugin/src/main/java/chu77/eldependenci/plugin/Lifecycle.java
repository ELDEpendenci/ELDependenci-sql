package chu77.eldependenci.plugin;

import chu77.eldependenci.plugin.configmanager.Dbconfig;
import chu77.eldependenci.plugin.sqlmanager.SQLDataSourceManager;
import chu77.eldependenci.sql.SQLDataSource;
import com.ericlam.mc.eld.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class Lifecycle implements ELDLifeCycle {

    @Inject
    private Dbconfig dbconfig;

    @Inject
    private SQLDataSource sqlDataSource;

    @Override
    public void onEnable(JavaPlugin javaPlugin) {
        if (dbconfig.enable){ // 如果 sql 已被啟用
            javaPlugin.getLogger().info("正在初始化 mysql....");
            ((SQLDataSourceManager)sqlDataSource).initialize(); // 初始化sql
        }
    }

    @Override
    public void onDisable(JavaPlugin javaPlugin) {
    }
}
