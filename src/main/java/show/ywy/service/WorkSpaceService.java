package show.ywy.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import show.ywy.entity.CreateLink;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.entity.WorkSpace;
import show.ywy.result.Result;

/**
 * @author yzs
 */
public abstract class WorkSpaceService {

    public abstract Result<Boolean> invitationCode(String code);

    public abstract Result<JSONObject> login(CreateLink createLink);

    public abstract Result<?> data(IntoWorkSpace intoWorkSpace);

    public abstract Result<Boolean> delete();


    public String createLink() {
        return;
    }

    public String createWorkSpaceUuid(String code) {
        return;
    }

    public WorkSpace init(String link) {
        return new WorkSpace();
    }

    public void loginOneUser(String password) {

    }
}
