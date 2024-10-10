package show.ywy.service.file.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.service.file.entity.FileEntity;
import show.ywy.service.file.entity.FileSaveType;
import show.ywy.service.file.service.FileService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yzs
 */
@Service(FileSaveType.Name.MEMORY)
public class FileMemoryServiceImpl extends FileService {

    @Override
    protected void downloadImpl(FileEntity fileEntity, HttpServletResponse response) {

    }

    @Override
    public boolean uploadImpl(FileEntity fileEntity, MultipartFile multipartFile) {
        return false;
    }


    @Override
    public boolean deleteImpl(FileEntity fileName) {
        return false;
    }
}
