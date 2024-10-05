package show.ywy.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import show.ywy.config.TimeConfig;
import show.ywy.config.UserConfig;
import show.ywy.db.UserMemory;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.*;
import show.ywy.result.ErrorCode;
import show.ywy.result.Result;
import show.ywy.service.WorkSpaceService;

import java.util.Objects;

/**
 * @author yzs
 */
@Service
@RequiredArgsConstructor
public class WorkSpaceServiceImpl extends WorkSpaceService {


    private final UserConfig userConfig;


    /**
     * 邀请码是否有效
     *
     * @return boolean
     */
    public Result<Boolean> invitationCode(String code) {
        return Result.ok(userConfig.getCode().contains(code));
    }

    /**
     * 创建一个工作空间
     *
     * @return java.lang.String
     * @since 2024/9/27
     */
    public Result<JSONObject> createWorkSpace(CreateLink createLink) {
        String username = createLink();
        String spaceUuid = createWorkSpaceUuid(createLink.getSpaceCode());
        // create user
        UserMemory.putUser(username, spaceUuid, TimeConfig.LINK_TIMEOUT);
        WorkSpace workSpace = init(username);
        // auto login
        StpUtil.login(username + "@" + spaceUuid);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        // create workspace
        WorkSpaceMemory.putWorkSpace(spaceUuid, workSpace, TimeConfig.WORK_SPACE_TIMEOUT);
        return Result.ok(JSONUtil.createObj().set("tokenInfo",tokenInfo)
                .set("key", SecureTool.encrypt(username)).set("id", username));
    }


    /**
     * 客户端进入工作空间
     *
     * @return link
     */
    public Result<String> into(IntoWorkSpace intoWorkSpace) {
        // link 是否有效
        String link = intoWorkSpace.getLink();
        String code = intoWorkSpace.getCode();
        if (UserMemory.isNotValid(link)) {
            return Result.error(ErrorCode.LINK_NOT_EXIST);
        }
        // code 是否正确
        String spaceUuid = UserMemory.getSpaceId(link);
        if (!spaceUuid.endsWith(code)) {
            return Result.error(ErrorCode.CODE_ERROR);
        }
        // 判断workspace是否存在
        boolean notValid = WorkSpaceMemory.isNotValid(spaceUuid);
        if (notValid) {
            return Result.error(ErrorCode.WORKSPACE_NOT_EXIST);
        }
        // link
        String newLink = createLink();
        UserMemory.putUser(newLink, spaceUuid, TimeConfig.LINK_TIMEOUT);
        StpUtil.login(link + StrPool.AT + spaceUuid);
        // 返回
        JSONObject result = JSONUtil.createObj().set("login", StrUtil.isNotBlank(newLink));
        result.set("key", SecureTool.encrypt(newLink));
        result.set("link", newLink);
        return Result.ok(newLink);
    }

    /**
     * 刷新，进入工作空间时验证
     *
     * @return boolean
     */
    public Result<Boolean> checkLoginKey(JSONObject data) {
        String link = data.getStr("link");
        boolean notValid = UserMemory.isNotValid(link);
        if (notValid) {
            return Result.error(ErrorCode.LINK_NOT_EXIST);
        }
        String key = data.getStr("key");
        String decrypt = SecureTool.decrypt(key);
        if (Objects.equals(link, decrypt)) {
            return Result.ok(true);
        } else {
            return Result.error(ErrorCode.LINK_NOT_EXIST);
        }
    }

    @Override
    public Result<FlashVo> flash() {
        String loginId = StpUtil.getLoginIdAsString();
        String spaceId = StrUtil.split(loginId, StrPool.AT).get(1);
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(spaceId);
        FlashVo flashVo = new FlashVo();
        flashVo.setWorkSpace(workSpace);
        return Result.ok(flashVo);
    }

    /**
     * 删除工作空间
     *
     * @return boolean
     */
    public Result<Boolean> delete() {
        String spaceUuid = StpUtil.getLoginIdAsString().split("\\|")[1];
        WorkSpaceMemory.getInstance().remove(spaceUuid);
        return Result.ok(true);
    }


    public Result<Boolean> isLogin(@RequestBody JSONObject data) {
        String link = data.getStr("link");
        String spaceId = UserMemory.getInstance().get(link);
        if (spaceId == null) {
            return Result.ok(false);
        }
        return Result.ok(StpUtil.isLogin(link + "|" + spaceId));
    }

}
