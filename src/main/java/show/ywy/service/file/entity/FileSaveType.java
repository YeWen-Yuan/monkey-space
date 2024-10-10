package show.ywy.service.file.entity;

import lombok.Getter;

/**
 * @author yzs
 */
@Getter
public enum FileSaveType {
    /**
     * 内存
     */
    MEMORY(Name.MEMORY),
    /**
     * OOS
     */
    OOS(Name.OOS),
    /**
     * 本地
     */
    LOCAL(Name.LOCAL);

    private final String service;

    FileSaveType(String memory) {
        service = memory;
    }

    public String service() {
        return service;
    }

    public static class Name {
        public static final String MEMORY = "fileMemoryService";
        public static final String OOS = "OSSFileService";
        public static final String LOCAL = "localFileService";
    }
}
