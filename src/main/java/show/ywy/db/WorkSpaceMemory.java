package show.ywy.db;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Singleton;
import show.ywy.entity.WorkSpace;

/**
 * @author yzs
 */
public class WorkSpaceMemory extends TimedCache<String, WorkSpace> {

    public WorkSpaceMemory(long timeout) {
        super(timeout);
    }

    public static WorkSpaceMemory getInstance() {
        return Singleton.get(WorkSpaceMemory.class, 1000 * 60 * 60L);
    }

    public static WorkSpace getWorkSpace(String key) {
        return getInstance().get(key);
    }

}
