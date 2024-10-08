package show.ywy.db;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Singleton;
import show.ywy.entity.UserInfo;

import java.util.concurrent.TimeUnit;

/**
 * @author yzs
 */
public class UserMemory extends TimedCache<String, UserInfo> {

    public UserMemory(long timeout) {
        super(timeout);
    }

    public static UserMemory getInstance() {
        return Singleton.get(UserMemory.class, TimeUnit.MINUTES.toMillis(60) * 5);
    }

    public static boolean isNotValid(String username) {
        return !getInstance().containsKey(username);
    }

    public static UserInfo getUserInfo(String username) {
        return getInstance().get(username);
    }

    public static void putUser(String username, UserInfo userInfo, long timeout) {
        getInstance().put(username, userInfo, timeout);
    }
}
