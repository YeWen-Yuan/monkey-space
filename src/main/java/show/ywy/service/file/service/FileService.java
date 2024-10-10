package show.ywy.service.file.service;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.result.Result;
import show.ywy.service.WorkSpaceMemoryService;
import show.ywy.service.file.entity.FileEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public abstract class FileService extends WorkSpaceMemoryService {

    public static String choiceFileService(FileEntity fileEntity) {
        return fileEntity.getFileSaveType().service();
    }

    @SaCheckLogin
    public Result<JSONObject> delete(FileEntity fileEntity) {
        boolean delete = deleteImpl(fileEntity);
        if (delete) {
            workspaceRemove(fileEntity);
        }
        return delete ? Result.ok() : Result.error();
    }

    @SaCheckLogin
    public void download(FileEntity fileEntity, HttpServletResponse response) {
        downloadImpl(fileEntity, response);
    }

    @SaCheckLogin
    public Result<JSONObject> upload(MultipartFile multipartFile) {
        FileEntity fileEntity = new FileEntity(multipartFile);
        fileEntity.setUuid(UUID.fastUUID().toString());
        boolean upload = uploadImpl(fileEntity, multipartFile);
        if (upload) {
            workspaceSave(fileEntity);
        }
        return upload ? Result.ok(JSONUtil.parseObj(fileEntity)) : Result.error();
    }

    // 核心实现
    protected abstract void downloadImpl(FileEntity fileEntity, HttpServletResponse response);

    protected abstract boolean uploadImpl(FileEntity fileEntity, MultipartFile file);

    protected abstract boolean deleteImpl(FileEntity fileEntity);

    protected HttpServletResponse fileResponse(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        return response;
    }

    protected void jsonResponse(HttpServletResponse response, int status, String message) {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            JSONObject json = JSONUtil.createObj().set(Result.Magic.CODE, status).set(Result.Magic.MESSAGE, message);
            response.getWriter().write(json.toJSONString(0));
        } catch (IOException e) {
            log.error(message, e);
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                log.error("response close error", e);
            }
        }
    }


}
