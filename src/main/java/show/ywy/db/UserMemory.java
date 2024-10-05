package show.ywy.db;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Singleton;

import java.util.concurrent.TimeUnit;

/**
 * @author yzs
 */
public class UserMemory extends TimedCache<String, String> {

    public UserMemory(long timeout) {
        super(timeout);
    }

    public static UserMemory getInstance() {
        return Singleton.get(UserMemory.class, TimeUnit.MINUTES.toMillis(60) * 5);
    }

    public static boolean isNotValid(String link) {
        return !getInstance().containsKey(link);
    }

    public static String getSpaceId(String link) {
        return getInstance().get(link);
    }

    public static void putUser(String link, String spaceId, long timeout) {
        getInstance().put(link, spaceId, timeout);
    }
}
