package show.ywy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import show.ywy.result.Result;
import show.ywy.service.ClipboardService;

/**
 * @author yzs
 */
@RestController
@RequestMapping("clipboard")
@RequiredArgsConstructor
public class ClipboardController {

    private final ClipboardService clipboardService;

    @PostMapping("add")
    @SaCheckLogin
    public Result<Boolean> addClipboard(@RequestBody JSONObject text) {
        return Result.ok(clipboardService.addClipboard(text.getStr("content")));
    }

}
