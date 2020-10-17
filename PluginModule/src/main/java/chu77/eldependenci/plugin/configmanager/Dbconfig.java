package chu77.eldependenci.plugin.configmanager;

import com.ericlam.mc.eld.annotations.Resource;
import com.ericlam.mc.eld.components.Configuration;

@Resource(locate = "db.yml")
public class Dbconfig extends Configuration {
    public String host;
    public int port;
    public String database;
    public String username;
    public String password;
    public boolean useSSL;
    public DatabasePool pool;
    public String chatFormatPlaceholder;
    public boolean enable;

    public static class DatabasePool {
        public String name;
        public int minSize;
        public int maxSize;
        public long connectionTimeout;
        public long idleTimeout;
        public long maxLifeTime;
    }


}

