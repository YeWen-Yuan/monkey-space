package show.ywy.db;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Singleton;
import show.ywy.entity.Clipboard;
import show.ywy.entity.WorkSpace;

import java.util.List;

/**
 * @author yzs
 */
public class WorkSpaceMemory extends TimedCache<String, WorkSpace> {

    public WorkSpaceMemory() {
        super(1000 * 60 * 60L);
    }

    public static WorkSpaceMemory getInstance() {
        return Singleton.get(WorkSpaceMemory.class);
    }

    public static WorkSpace getWorkSpace(String key) {
        WorkSpace workSpace = getInstance().get(key);
        List<Clipboard> clipboardHistory = workSpace.getClipboardHistory();
        ListUtil.sort(clipboardHistory, (o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        workSpace.setClipboardHistory(clipboardHistory);
        return workSpace;
    }

    public static void putWorkSpace(String key, WorkSpace workSpace, long timeout) {
        getInstance().put(key, workSpace, timeout);
    }

    public static boolean isNotValid(String key) {
        return !getInstance().containsKey(key);
    }
}
