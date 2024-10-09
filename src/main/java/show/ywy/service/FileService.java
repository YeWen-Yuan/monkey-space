package show.ywy.service;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.entity.FileEntity;
import show.ywy.result.Result;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class FileService {

    @SaCheckLogin
    public Result<JSONObject> delete(FileEntity fileEntity, String fileName) {
        boolean delete = delete(fileEntity);
        if (delete) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @SaCheckLogin
    public void download(FileEntity fileEntity, HttpServletResponse response) {
        File file = download(new FileEntity());
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

    @SaCheckLogin
    public Result<JSONObject> upload(MultipartFile multipartFile) {
        boolean upload = upload(new FileEntity(), multipartFile);
        if (upload) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    protected void record(FileEntity fileEntity) {
        String loginId = StpUtil.getLoginIdAsString();

    }

    protected abstract File download(FileEntity fileEntity);

    protected abstract boolean upload(FileEntity fileEntity, MultipartFile file);

    protected abstract boolean delete(FileEntity fileEntity);


}
