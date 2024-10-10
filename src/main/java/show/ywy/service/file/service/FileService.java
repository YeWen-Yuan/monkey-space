package show.ywy.service.file.service;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.service.WorkSpaceMemoryService;
import show.ywy.service.file.entity.FileEntity;
import show.ywy.result.Result;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public abstract class FileService extends WorkSpaceMemoryService {

    public static String choiceFileService(FileEntity fileEntity) {
        return fileEntity.getFileSaveType().service();
    }

    @SaCheckLogin
    public Result<JSONObject> delete(FileEntity fileEntity, String fileName) {
        boolean delete = deleteImpl(fileEntity);
        return delete ? Result.ok() : Result.error();
    }

    @SaCheckLogin
    public void download(FileEntity fileEntity, HttpServletResponse response) {
        downloadImpl(fileEntity, response);
    }

    @SaCheckLogin
    public Result<JSONObject> upload(MultipartFile multipartFile) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(multipartFile.getOriginalFilename());
        fileEntity.setSize(multipartFile.getSize());
        boolean upload = uploadImpl(fileEntity, multipartFile);
        return upload ? Result.ok() : Result.error();
    }

    // 核心实现
    protected abstract void downloadImpl(FileEntity fileEntity, HttpServletResponse response);

    protected abstract boolean uploadImpl(FileEntity fileEntity, MultipartFile file);

    protected abstract boolean deleteImpl(FileEntity fileEntity);


}
