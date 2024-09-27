package show.ywy.entity;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;

import java.nio.charset.StandardCharsets;

/**
 * @author yzs
 */
public class SecureTool {

    private final static String PRIVATE_KEY = new FileReader(ResourceUtil.getResource("key/private.key").getFile()).readString();
    private final static String PUBLIC_KEY = new FileReader(ResourceUtil.getResource("key/public.key").getFile()).readString();

    public static String getPublicKey() {
        return PUBLIC_KEY;
    }

    /**
     * 公钥加密
     *
     * @param data 数据
     * @return java.lang.String
     * @since 2024/7/30
     */
    public static String encrypt(String data) {
        return SecureUtil.rsa(PRIVATE_KEY, PUBLIC_KEY).encryptBase64(data, StandardCharsets.UTF_8, KeyType.PublicKey);
    }

    /**
     * 私钥解密
     *
     * @param data 数据
     * @return java.lang.String
     * @since 2024/7/30
     */
    public static String decrypt(String data) {
        return SecureUtil.rsa(PRIVATE_KEY, PUBLIC_KEY).decryptStr(data, KeyType.PrivateKey);
    }

}
