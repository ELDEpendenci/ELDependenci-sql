package chu77.eldependenci.sql;

import chu77.eldependenci.sql.config.Dbconfig;
import chu77.eldependenci.sql.manager.SQLDataSourceManager;
import com.ericlam.mc.eld.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class SQLAddonLifecycle implements ELDLifeCycle {

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
