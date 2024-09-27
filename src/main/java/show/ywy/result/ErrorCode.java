package show.ywy.result;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // SYSTEM
    SYSTEM_ERROR(50001, "系统异常"),
    PARAM_ERROR(50002, "参数错误"),
    BIZ_ERROR(50003, "业务异常"),
    SQL_ERROR(50004, "数据库错误"),
    PARAM_EMPTY_ERROR(50005, "参数为空"),
    // USER
    PASSWORD_ERROR(40003, "密码错误"),
    USER_NOT_LOGIN(40005, "用户未登录"),
    USER_LOCK(40013, "邀请码错误"),

    WORKSPACE_NOT_EXIST(5006, "工作空间已失效"),
    CODE_ERROR(5007, "空间码错误"),
    LINK_NOT_EXIST(5008, "链接无效"),
    ;


    private final int code;
    private final String message;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
