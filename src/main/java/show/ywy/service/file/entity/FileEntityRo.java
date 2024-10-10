package show.ywy.service.file.entity;

import lombok.Data;

/**
 * File 实体
 *
 * @author yzs
 */
@Data
public class FileEntityRo {
    private String uuid;
    private String fileName;
    private Long size;
    private FileSaveType fileSaveType;
    private OssFile oos_file;

    public FileEntity toFileEntity() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUuid(uuid);
        fileEntity.setFileName(fileName);
        fileEntity.setFileSaveType(fileSaveType);
        return fileEntity;
    }
}
