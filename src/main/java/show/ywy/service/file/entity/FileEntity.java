package show.ywy.service.file.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * File 实体
 *
 * @author yzs
 */
@Data
public class FileEntity {
    private String uuid;
    private String fileName;
    private Long size;
    private FileSaveType fileSaveType;
    // 内存的file
    private File file;
    // OOS file
    private OssFile oos_file;

    public FileEntity() {
    }

    public FileEntity(MultipartFile file) {
        this.fileName = file.getOriginalFilename();
        this.size = file.getSize();
    }

    public long fileSize() {
        return file == null ? 0L : file.length();
    }

    public FileSaveType getFileSaveType() {
        if (fileSaveType != null) {
            return fileSaveType;
        }
        boolean b = fileSize() / 1024 / 1024 > 10;
        FileSaveType fileSaveType1 = b ? FileSaveType.LOCAL : FileSaveType.MEMORY;
        setFileSaveType(fileSaveType1);
        return fileSaveType1;
    }
}
