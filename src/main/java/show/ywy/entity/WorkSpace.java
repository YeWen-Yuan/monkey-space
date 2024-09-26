package show.ywy.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzs
 */
@Data
public class WorkSpace {
    // 剪贴板
    private List<Clipboard> clipboardHistory = new ArrayList<>();
    // 文件列表
    private List<FileEntity> fileList = new ArrayList<>();
    // link 列表
    private List<String> linkList = new ArrayList<>();
}
