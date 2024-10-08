package show.ywy.service;

import cn.hutool.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.entity.FileEntity;
import show.ywy.result.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yzs
 */
public abstract class FileService {

    public abstract Result<JSONObject> delete(String fileName);

    public abstract void download(FileEntity fileEntity, HttpServletResponse response);

    public abstract Result<JSONObject> upload(MultipartFile multipartFile);
}
