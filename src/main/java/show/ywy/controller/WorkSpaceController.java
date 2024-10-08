package show.ywy.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("workspace/code/invitation")
    public Result<Boolean> isValidInvitationCode(@RequestBody JSONObject code) {
        return workSpaceService.invitationCode(code.getStr("code"));
    }

    @PostMapping("workspace/create")
    public Result<JSONObject> linkValue(@RequestBody CreateLink createLink) {
        if (!workSpaceService.invitationCode(createLink.getInvitationCode()).getData()) {
            return Result.error(USER_LOCK);
        }
        return workSpaceService.login(createLink);
    }

    @PostMapping("workspace/into")
    public Result<?> into(@RequestBody IntoWorkSpace intoWorkSpace) {
        return workSpaceService.data(intoWorkSpace);
    }


    @DeleteMapping("workspace/delete")
    public Result<Boolean> deleteLink() {
        return workSpaceService.delete();
    }
}
