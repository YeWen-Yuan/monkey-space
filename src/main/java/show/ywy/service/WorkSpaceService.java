package show.ywy.service;

import cn.hutool.json.JSONObject;
import show.ywy.entity.CreateLink;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.result.Result;

/**
 * @author yzs
 */
public abstract class WorkSpaceService {

    public abstract Result<Boolean> invitationCode(String code);

    public abstract Result<JSONObject> login(CreateLink createLink);

    public abstract Result<?> data(IntoWorkSpace intoWorkSpace);

    public abstract Result<Boolean> delete();

}
