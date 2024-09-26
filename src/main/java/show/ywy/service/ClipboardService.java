package show.ywy.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.Clipboard;
import show.ywy.entity.WorkSpace;

import java.util.List;

/**
 * @author yzs
 */
@Service
public class ClipboardService {

    public boolean addClipboard(String text) {
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(StpUtil.getLoginIdAsString());
        workSpace.getClipboardHistory().add(new Clipboard(text));
        return true;
    }

    public List<Clipboard> getClipboard() {
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(StpUtil.getLoginIdAsString());
        return workSpace.getClipboardHistory();
    }
}
