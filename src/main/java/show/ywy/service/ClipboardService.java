package show.ywy.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.Clipboard;
import show.ywy.entity.WorkSpace;
import show.ywy.util.UserUtil;

/**
 * @author yzs
 */
@Service
public class ClipboardService {

    public boolean addClipboard(String text) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(UserUtil.getWorkSpaceId(loginIdAsString));
        workSpace.getClipboardHistory().add(new Clipboard(text));
        return true;
    }

}
