package show.ywy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import show.ywy.entity.Clipboard;
import show.ywy.result.Result;
import show.ywy.service.ClipboardService;

import javax.annotation.Resource;
import java.util.List;

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
