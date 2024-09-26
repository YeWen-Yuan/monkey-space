package show.ywy.exception;

import lombok.RequiredArgsConstructor;

/**
 * @author yzs
 */
@RequiredArgsConstructor
public class UserNotLoginException extends RuntimeException {

    private final String message;

    @Override
    public String getMessage() {
        return this.message + " is Empty!";
    }
}
