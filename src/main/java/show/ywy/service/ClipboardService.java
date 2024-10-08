package show.ywy.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import show.ywy.db.UserMemory;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.Clipboard;
import show.ywy.entity.WorkSpace;

/**
 * @author yzs
 */
@Service
public class ClipboardService {

    public boolean addClipboard(String text) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        String workSpaceUuid = UserMemory.getUserInfo(loginIdAsString).getWorkSpaceUuid();
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(workSpaceUuid);
        workSpace.getClipboardHistory().add(new Clipboard(text));
        return true;
    }

}
