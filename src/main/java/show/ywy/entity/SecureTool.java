package show.ywy.entity;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;

import java.nio.charset.StandardCharsets;

/**
 * @author yzs
 */
public class SecureTool {



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

    public static void main(String[] args) {
        String encrypt = encrypt("123");
        System.out.println(encrypt);
        System.out.println(decrypt(encrypt));
    }

}
