package show.ywy.controller;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.result.Result;
import show.ywy.service.file.entity.FileEntity;
import show.ywy.service.file.entity.FileEntityRo;
import show.ywy.service.file.service.FileService;

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
        String fileServiceName = FileService.choiceFileService(new FileEntity(file));
        return fileServiceMap.get(fileServiceName).upload(file);
    }

    @PostMapping("download")
    public void downloadFile(@RequestBody FileEntityRo data, HttpServletResponse response) {
        fileServiceMap.get(FileService.choiceFileService(data.toFileEntity())).download(data.toFileEntity(), response);
    }

    @PostMapping("delete")
    @ResponseBody
    public Result<JSONObject> deleteFile(@RequestBody FileEntityRo data) {
        return fileServiceMap.get(FileService.choiceFileService(data.toFileEntity())).delete(data.toFileEntity());
    }

}
