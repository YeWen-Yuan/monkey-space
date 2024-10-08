package show.ywy.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.entity.FileEntity;
import show.ywy.result.Result;
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
    @ResponseBody
    public Result<JSONObject> uploadFile(MultipartFile multipartFile) {
       return fileService.upload(multipartFile);
    }

    @PostMapping("download")
    public void downloadFile(@RequestBody FileEntity data, HttpServletResponse response) {
        fileService.download(data, response);
    }

    @PostMapping("delete")
    @ResponseBody
    public Result<JSONObject> deleteFile(@RequestBody FileEntity data) {
       return fileService.delete(data.getFileName());
    }

}
