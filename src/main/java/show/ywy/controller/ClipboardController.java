package show.ywy.controller;

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
    public Result<Boolean> addClipboard(@RequestBody String text) {
        return Result.ok(clipboardService.addClipboard(text));
    }

}
