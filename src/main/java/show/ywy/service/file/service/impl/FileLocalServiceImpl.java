package show.ywy.service.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.service.file.entity.FileEntity;
import show.ywy.service.file.service.FileService;

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
    public boolean uploadImpl(FileEntity fileEntity, MultipartFile multipartFile) {
        try {
            String pathname = filePath + StpUtil.getLoginIdAsString() + "/" + multipartFile.getOriginalFilename();
            FileWriter.create(new File(pathname)).writeFromStream(multipartFile.getInputStream());
            return true;
        } catch (IOException e) {
            log.error("upload file error", e);
            return false;
        }
    }

    @Override
    public void downloadImpl(FileEntity fileEntity, HttpServletResponse response) {
        String pathname = filePath + StpUtil.getLoginIdAsString() + "/" + fileEntity.getFileName();
    }

    @Override
    public boolean deleteImpl(FileEntity fileName) {
        String pathname = filePath + StpUtil.getLoginIdAsString() + "/" + fileName;
        try {
            FileUtil.del(pathname);
        } catch (IORuntimeException e) {
            log.error("delete file error", e);
            return false;
        }
        return true;
    }
}

