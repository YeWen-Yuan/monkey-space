package show.ywy.util;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import show.ywy.exception.UserNotLoginException;

/**
 * @author yzs
 */
public class UserUtil {

    public static String getWorkSpaceId(String loginId){
        try {
            return StrUtil.split(loginId, StrPool.AT).get(1);
        } catch (Exception e) {
            throw new UserNotLoginException("user is not login!");
        }
    }
}
