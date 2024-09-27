package show.ywy.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import show.ywy.db.LinkMemory;
import show.ywy.entity.CreateLink;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.entity.SecureTool;
import show.ywy.result.Result;
import show.ywy.service.WorkSpaceService;

import java.util.Objects;

import static show.ywy.result.ErrorCode.USER_LOCK;

/**
 * @author yzs
 */
@RestController
@RequiredArgsConstructor
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;

    @PostMapping("code/invitation")
    public Result<Boolean> isValidInvitationCode(@RequestBody JSONObject code) {
        return Result.ok(workSpaceService.invitationCode(code.getStr("code")));
    }

    @PostMapping("workspace/create")
    public Result<JSONObject> linkValue(@RequestBody CreateLink createLink) {
        if (!workSpaceService.invitationCode(createLink.getInvitationCode())) {
            return Result.error(USER_LOCK);
        }
        String workSpace = workSpaceService.createWorkSpace(createLink);
        return Result.ok(JSONUtil.createObj().set("key", SecureTool.encrypt(workSpace)).set("id", workSpace));
    }

    @PostMapping("workspace/into")
    public Result<JSONObject> into(@RequestBody IntoWorkSpace intoWorkSpace) {
        String into = workSpaceService.into(intoWorkSpace);
        JSONObject result = JSONUtil.createObj().set("login", StrUtil.isNotBlank(into));
        result.set("key", SecureTool.encrypt(into));
        return Result.ok(result);
    }


    @DeleteMapping("workspace/delete")
    public Result<Boolean> deleteLink() {
        return Result.ok(workSpaceService.delete());
    }

    @PostMapping("workspace/checkLoginKey")
    public Result<Boolean> checkLoginKey(@RequestBody JSONObject data) {
        String link = data.getStr("link");
        boolean notValid = LinkMemory.isNotValid(link);
        if (notValid) {
            return Result.ok(false);
        }
        String key = data.getStr("key");
        String decrypt = SecureTool.decrypt(key);
        if (Objects.equals(link, decrypt)) {
            return Result.ok(true);
        } else {
            return Result.ok(false);
        }
    }
}
