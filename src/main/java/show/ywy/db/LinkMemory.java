package show.ywy.db;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Singleton;

import java.util.concurrent.TimeUnit;

/**
 * @author yzs
 */
public class LinkMemory extends TimedCache<String, String> {

    public LinkMemory(long timeout) {
        super(timeout);
    }

    public static LinkMemory getInstance() {
        return Singleton.get(LinkMemory.class, TimeUnit.MINUTES.toMillis(60) * 5);
    }

    public static boolean isNotValid(String link) {
        return !getInstance().containsKey(link);
    }

    public static String getSpaceId(String link) {
        return getInstance().get(link);
    }
}
