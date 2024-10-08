package show.ywy.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import show.ywy.exception.BizException;
import show.ywy.exception.EmptyRequestParam;
import show.ywy.exception.PasswordEncodeException;
import show.ywy.exception.UserNotLoginException;

import java.sql.SQLException;

/**
 * @author yzs
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> exception(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.PARAM_ERROR);
    }

    /**
     * 密码解密异常
     */
    @ExceptionHandler(PasswordEncodeException.class)
    public Result<?> exception(PasswordEncodeException e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.PASSWORD_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public Result<?> exception(SQLException e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.SQL_ERROR);
    }

    @ExceptionHandler(EmptyRequestParam.class)
    public Result<?> exception(EmptyRequestParam e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.PARAM_EMPTY_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> exception(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.PARAM_ERROR);
    }

    @ExceptionHandler(BizException.class)
    public Result<?> exception(BizException e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.BIZ_ERROR);
    }

    @ExceptionHandler(UserNotLoginException.class)
    public Result<?> exception(UserNotLoginException e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.USER_NOT_LOGIN);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exception(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(ErrorCode.SYSTEM_ERROR);
    }
}
