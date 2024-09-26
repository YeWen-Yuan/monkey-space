package show.ywy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import show.ywy.entity.FileEntityVo;

import java.util.List;

/**
 * @author yzs
 */
@Controller
@RequestMapping("file")
public class FileController {

    @PostMapping("upload")
    public String uploadFile() {
        return "upload";
    }

    @PostMapping("download")
    public String downloadFile() {
        return "download";
    }

    @PostMapping("delete")
    public String deleteFile() {
        return "delete";
    }

    @PostMapping("list")
    public List<FileEntityVo> listFile() {
        return null;
    }

}
