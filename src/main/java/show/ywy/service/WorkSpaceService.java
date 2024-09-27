package show.ywy.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import show.ywy.config.TimeConfig;
import show.ywy.config.UserConfig;
import show.ywy.db.LinkMemory;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.CreateLink;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.entity.WorkSpace;
import show.ywy.result.Result;

/**
 * @author yzs
 */
@Service
@RequiredArgsConstructor
public class WorkSpaceService {


    private final UserConfig userConfig;

    public boolean invitationCode(String code) {
        return userConfig.getCode().contains(code);
    }

    public String createWorkSpace(CreateLink createLink) {
        // code
        String spaceCode = createLink.getSpaceCode();
        // link
        String link = RandomUtil.randomString(50);
        // key
        String spaceUuid = RandomUtil.randomString(20) + "|" + spaceCode;
        // workspace
        WorkSpace workSpace = new WorkSpace();
        workSpace.setLinkList(ListUtil.toList(link));
        WorkSpaceMemory.getInstance().put(spaceUuid, workSpace, TimeConfig.WORK_SPACE_TIMEOUT);
        StpUtil.login(link + "|" + spaceUuid);
        // link
        LinkMemory.getInstance().put(link, spaceUuid, TimeConfig.LINK_TIMEOUT);
        return link;
    }


    public Result<Boolean> isLogin(@RequestBody JSONObject data) {
        String link = data.getStr("link");
        String spaceId = LinkMemory.getInstance().get(link);
        if (spaceId == null) {
            return Result.ok(false);
        }
        return Result.ok(StpUtil.isLogin(link + "|" + spaceId));
    }

    public String into(IntoWorkSpace intoWorkSpace) {
        String link = intoWorkSpace.getLink();
        String spaceUuid = LinkMemory.getInstance().get(link);
        if (spaceUuid == null) {
            return "";
        }
        StpUtil.login(link + "|" + spaceUuid);
        return link;
    }


    public boolean delete() {
        String spaceUuid = StpUtil.getLoginIdAsString().split("\\|")[1];
        WorkSpaceMemory.getInstance().remove(spaceUuid);
        return true;
    }

}
