package show.ywy.db;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Singleton;

import java.util.concurrent.TimeUnit;

/**
 * @author yzs
 */
public class SpaceMemory extends TimedCache<String, String> {

    public SpaceMemory(long timeout) {
        super(timeout);
    }

    public static SpaceMemory getInstance() {
        return Singleton.get(SpaceMemory.class, TimeUnit.MINUTES.toMillis(60) * 5);
    }


}
