package show.ywy.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import show.ywy.entity.CreateLink;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.result.Result;
import show.ywy.service.WorkSpaceService;

import javax.annotation.Resource;

import static show.ywy.result.ErrorCode.USER_LOCK;

/**
 * @author yzs
 */
@RestController
public class WorkSpaceController {

    @Resource
    private WorkSpaceService workSpaceService;

    @PostMapping("code/invitation")
    public Result<Boolean> isValidInvitationCode(@RequestBody JSONObject code) {
        return Result.ok(workSpaceService.invitationCode(code.getStr("code")));
    }

    @PostMapping("workspace/create")
    public Result<String> linkValue(@RequestBody CreateLink createLink) {
        if (!workSpaceService.invitationCode(createLink.getInvitationCode())) {
            return Result.error(USER_LOCK);
        }
        return Result.ok(workSpaceService.createWorkSpace(createLink));
    }

    @PostMapping("workspace/share")
    public Result<String> share() {
        return Result.ok(workSpaceService.share());
    }

    @PostMapping("workspace/into")
    public Result<Boolean> into(@RequestBody IntoWorkSpace intoWorkSpace) {
        return Result.ok(workSpaceService.into(intoWorkSpace));
    }


    @DeleteMapping("workspace/delete")
    public Result<Boolean> deleteLink() {
        return Result.ok(workSpaceService.delete());
    }

    @PostMapping("workspace/isLogin")
    public Result<Boolean> isLogin() {
        return Result.ok(StpUtil.isLogin());
    }
}
