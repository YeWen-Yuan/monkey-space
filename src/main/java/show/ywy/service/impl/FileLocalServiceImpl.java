package show.ywy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.entity.FileEntity;
import show.ywy.result.Result;
import show.ywy.service.FileService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author yzs
 */
@Slf4j
@Service("localFileService")
public class FileLocalServiceImpl extends FileService {

    private final String filePath = "D:/test/";

    @Override
    public Result<JSONObject> upload(MultipartFile multipartFile) {
        try {
            String pathname = filePath + StpUtil.getLoginIdAsString() + "/" + multipartFile.getOriginalFilename();
            FileWriter.create(new File(pathname)).writeFromStream(multipartFile.getInputStream());
            return Result.ok();
        } catch (IOException e) {
            log.error("upload file error", e);
            return Result.error();
        }
    }

    @Override
    public void download(FileEntity fileEntity, HttpServletResponse response) {
        String pathname = filePath + StpUtil.getLoginIdAsString() + "/" + fileEntity.getFileName();
        File file = new File(pathname);
        if (!file.exists()) {
            log.error("file not exist");
            response.setStatus(404);
            response.setContentType("application/json");
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write("{\"code\":404,\"message\":\"file not exist\"}".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileEntity.getFileName());
        try {
            FileUtil.writeToStream(file, response.getOutputStream());
        } catch (IOException e) {
            log.error("download file error", e);
        }
    }

    @Override
    public Result<JSONObject> delete(String fileName) {
        String pathname = filePath + StpUtil.getLoginIdAsString() + "/" + fileName;
        try {
            FileUtil.del(pathname);
        } catch (IORuntimeException e) {
            log.error("delete file error", e);
            return Result.error();
        }
        return Result.ok();
    }
}

