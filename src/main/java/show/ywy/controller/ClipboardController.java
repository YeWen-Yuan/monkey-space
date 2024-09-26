package show.ywy.controller;

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
public class ClipboardController {

    @Resource
    private ClipboardService clipboardService;

    @PostMapping("add")
    public Result<Boolean> addClipboard(@RequestBody String text) {
        return Result.ok(clipboardService.addClipboard(text));
    }

    @GetMapping("get")
    public Result<List<Clipboard>> getClipboard() {
        return Result.ok(clipboardService.getClipboard());
    }
}
