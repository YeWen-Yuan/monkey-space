package show.ywy.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.service.FileService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yzs
 */
@Controller
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("upload")
    public void uploadFile(MultipartFile multipartFile) {
        fileService.upload(multipartFile);
    }

    @PostMapping("download")
    public void downloadFile(@RequestBody JSONObject data, HttpServletResponse response) {
        fileService.download(data.getStr("fileName"), response);
    }

    @PostMapping("delete")
    public void deleteFile(@RequestBody JSONObject data) {
        fileService.delete(data.getStr("fileName"));
    }

}
