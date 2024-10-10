package show.ywy.service.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ywy.db.UserMemory;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.service.file.entity.FileEntity;
import show.ywy.service.file.entity.FileSaveType;
import show.ywy.service.file.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yzs
 */
@Slf4j
@Service(FileSaveType.Name.MEMORY)
public class FileMemoryServiceImpl extends FileService {

    @Override
    protected void downloadImpl(FileEntity fileEntity, HttpServletResponse response) {
        try {
            String uuid = fileEntity.getUuid();
            List<FileEntity> fileList = WorkSpaceMemory.getWorkSpace(UserMemory.getUserInfo(StpUtil.getLoginIdAsString()).getWorkSpaceUuid()).getFileList();
            Map<String, FileEntity> map = CollStreamUtil.toMap(fileList, FileEntity::getUuid, fileEntity1 -> fileEntity1);
            File file = map.get(uuid).getFile();
            FileUtil.writeToStream(file, response.getOutputStream());
        } catch (IOException e) {
            log.error("下载文件失败", e);
            jsonResponse(response, 500, "下载文件失败");
        }finally {
            try {
                IoUtil.close(response.getOutputStream());
            } catch (IOException e) {
                jsonResponse(response, 500, "下载文件失败2");
            }
        }
    }

    private final String filePath = System.getProperty("user.dir") + "/static/temp/";

    @Override
    public boolean uploadImpl(FileEntity fileEntity, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return false;
        }
        fileEntity.setFileName(multipartFile.getOriginalFilename());
        File dest = FileUtil.touch(filePath + StpUtil.getLoginIdAsString() + "/" + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(dest);
        } catch (IOException e) {
            log.error("文件保存失败", e);
            return false;
        }
        fileEntity.setFile(dest);
        fileEntity.setFileSaveType(FileSaveType.MEMORY);
        return true;
    }


    @Override
    public boolean deleteImpl(FileEntity fileName) {
        return true;
    }
}
