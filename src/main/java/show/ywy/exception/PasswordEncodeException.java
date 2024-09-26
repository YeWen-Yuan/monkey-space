package show.ywy.exception;

/**
 * @author yzs
 */
public class PasswordEncodeException extends RuntimeException {
    public PasswordEncodeException() {
        super("Password encode error!");
    }
}
