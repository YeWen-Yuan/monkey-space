package show.ywy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import show.ywy.config.TimeConfig;
import show.ywy.config.UserConfig;
import show.ywy.db.UserMemory;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.*;
import show.ywy.result.ErrorCode;
import show.ywy.result.Result;
import show.ywy.service.WorkSpaceService;

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
    public Result<JSONObject> login(CreateLink createLink) {
        String username = RandomUtil.randomString(50);
        String spaceUuid = RandomUtil.randomString(20);
        // create user
        UserInfo userInfo = new UserInfo();
        userInfo.setWorkSpaceUuid(spaceUuid);
        userInfo.setWorkSpaceCode(createLink.getSpaceCode());
        UserMemory.putUser(username, userInfo, TimeConfig.LINK_TIMEOUT);
        StpUtil.login(username);
        // create workspace
        WorkSpace workSpace = new WorkSpace();
        WorkSpaceMemory.putWorkSpace(spaceUuid, workSpace, TimeConfig.WORK_SPACE_TIMEOUT);
        return Result.ok(JSONUtil.parseObj(new WorkSpaceVo(workSpace, StpUtil.getTokenValue(), StpUtil.getTokenName(), username)));
    }

    /**
     * 客户端进入工作空间
     *
     * @return link
     */
    public Result<JSONObject> data(IntoWorkSpace intoWorkSpace) {
        if (StpUtil.isLogin()) {
            String loginId = StpUtil.getLoginIdAsString();
            UserInfo userInfo = UserMemory.getUserInfo(loginId);
            if (userInfo == null) {
                return Result.error(ErrorCode.LINK_NOT_EXIST);
            }
            String workSpaceId = userInfo.getWorkSpaceUuid();
            return Result.ok(JSONUtil.createObj().set("data", WorkSpaceMemory.getWorkSpace(workSpaceId)));
        } else {
            // link 是否有效
            String username = intoWorkSpace.getLink();
            String password = intoWorkSpace.getCode();
            if (UserMemory.isNotValid(username)) {
                return Result.error(ErrorCode.LINK_NOT_EXIST);
            }
            // code 是否正确
            String userInfo = UserMemory.getUserInfo(username).getWorkSpaceCode();
            if (!userInfo.equalsIgnoreCase(password)) {
                return Result.error(ErrorCode.CODE_ERROR);
            }
            // 判断workspace是否存在
            String spaceId = UserMemory.getUserInfo(username).getWorkSpaceUuid();
            boolean notValid = WorkSpaceMemory.isNotValid(spaceId);
            if (notValid) {
                return Result.error(ErrorCode.WORKSPACE_NOT_EXIST);
            }
            StpUtil.login(username);
            return Result.ok(JSONUtil.parseObj(new WorkSpaceVo(null, StpUtil.getTokenValue(), StpUtil.getTokenName(), username)));
        }
    }

    /**
     * 删除工作空间
     *
     * @return boolean
     */
    public Result<Boolean> delete() {
        String loginId;
        try {
            loginId = StpUtil.getLoginIdAsString();
        } catch (Exception e) {
            return Result.ok(false);
        }
        String workSpaceUuid = UserMemory.getUserInfo(loginId).getWorkSpaceUuid();
        UserMemory.getInstance().remove(loginId);
        WorkSpaceMemory.getInstance().remove(workSpaceUuid);
        StpUtil.logout();
        return Result.ok(true);
    }

}
