package show.ywy.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import show.ywy.entity.CreateLink;
import show.ywy.entity.FlashVo;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.entity.WorkSpace;
import show.ywy.result.Result;

/**
 * @author yzs
 */
public abstract class WorkSpaceService {

    public abstract Result<Boolean> invitationCode(String code);

    public abstract Result<JSONObject> createWorkSpace(CreateLink createLink);

    public abstract Result<?> into(IntoWorkSpace intoWorkSpace);

    public abstract Result<Boolean> delete();

    public abstract Result<Boolean> checkLoginKey(JSONObject data);

    public abstract Result<FlashVo> flash();

    public String createLink() {
        return RandomUtil.randomString(50);
    }

    public String createWorkSpaceUuid(String code) {
        return RandomUtil.randomString(20) + "|" + code;
    }

    public WorkSpace init(String link) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setLinkList(ListUtil.toList(link));
        return workSpace;
    }

}
