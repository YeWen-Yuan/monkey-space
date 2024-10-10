package show.ywy.service;

import cn.dev33.satoken.stp.StpUtil;
import show.ywy.db.UserMemory;
import show.ywy.db.WorkSpaceMemory;
import show.ywy.entity.WorkSpace;
import show.ywy.service.file.entity.FileEntity;

import java.util.List;

/**
 * @author yzs
 */
public class WorkSpaceMemoryService {

    protected void workspaceSave(FileEntity fileEntity) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        String workSpaceUuid = UserMemory.getUserInfo(loginIdAsString).getWorkSpaceUuid();
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(workSpaceUuid);
        workSpace.getFileList().add(fileEntity);
    }

    protected void workspaceRemove(FileEntity fileEntity) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        String workSpaceUuid = UserMemory.getUserInfo(loginIdAsString).getWorkSpaceUuid();
        WorkSpace workSpace = WorkSpaceMemory.getWorkSpace(workSpaceUuid);
        List<FileEntity> fileList = workSpace.getFileList();
        fileList.removeIf(file -> file.getUuid().equals(fileEntity.getUuid()));
    }
}
