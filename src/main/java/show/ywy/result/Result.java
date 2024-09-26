package show.ywy.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("Success!");
        return result;
    }


    public static <T> Result<T> error(ErrorCode errorCode) {
        return result(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> error() {
        return result(500, "Server Error!", null);
    }

    public static <T> Result<T> ok(T t) {
        return result(200, "Success!", t);
    }

    private static <T> Result<T> result(int code, String msg, T data) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

}
