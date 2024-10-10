package show.ywy.service.file.entity;

import cn.hutool.core.io.FileUtil;
import lombok.Data;

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
    private boolean isMemoryFile = false;
    // 内存的file
    private File file;
    // OOS file
    private OssFile oos_file;

    public long fileSize() {
        return FileUtil.size(file);
    }


}
