package show.ywy.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.entity.FileEntity;
import show.ywy.result.Result;
import show.ywy.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yzs
 */
@Controller
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final Map<String, FileService> fileServiceMap;

    @PostMapping("upload")
    @ResponseBody
    public Result<JSONObject> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileServiceMap.get(FileEntity.getFileService(file.getSize())).upload(file);
    }

    @PostMapping("download")
    public void downloadFile(@RequestBody FileEntity data, HttpServletResponse response) {
        fileServiceMap.get(data.getFileService()).download(data, response);
    }

    @PostMapping("delete")
    @ResponseBody
    public Result<JSONObject> deleteFile(@RequestBody FileEntity data) {
        return fileServiceMap.get(data.getFileService()).delete(data.getFileName());
    }

}
