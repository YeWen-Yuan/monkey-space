package show.ywy.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yzs
 */
public abstract class FileService {

    public abstract void delete(String fileName);

    public abstract void download(String fileName, HttpServletResponse response);

    public abstract void upload(MultipartFile multipartFile);
}
