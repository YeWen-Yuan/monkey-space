package show.ywy.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import show.ywy.entity.CreateLink;
import show.ywy.entity.IntoWorkSpace;
import show.ywy.result.Result;
import show.ywy.service.WorkSpaceService;

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
        return workSpaceService.invitationCode(code.getStr("code"));
    }

    @PostMapping("workspace/create")
    public Result<JSONObject> linkValue(@RequestBody CreateLink createLink) {
        if (!workSpaceService.invitationCode(createLink.getInvitationCode()).getData()) {
            return Result.error(USER_LOCK);
        }
        return workSpaceService.createWorkSpace(createLink);
    }

    @PostMapping("workspace/into")
    public Result<?> into(@RequestBody IntoWorkSpace intoWorkSpace) {
        return workSpaceService.into(intoWorkSpace);
    }


    @DeleteMapping("workspace/delete")
    public Result<Boolean> deleteLink() {
        return workSpaceService.delete();
    }

    @PostMapping("workspace/checkLoginKey")
    public Result<Boolean> checkLoginKey(@RequestBody JSONObject data) {
        return workSpaceService.checkLoginKey(data);
    }
}
