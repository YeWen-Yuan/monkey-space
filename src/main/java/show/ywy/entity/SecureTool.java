package show.ywy.entity;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import java.io.File;
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

    public static void createKey() {
        try (HttpResponse execute = HttpUtil.createPost("http://web.chacuo.net/netrsakeypair")
                .form("data", "2048")
                .form("type", "rsakeypair")
                .form("arg", "t%3D1_o%3D0_p%3D")
                .header("Accept", "*/*")
                .header("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("proxy-connection", "keep-alive")
                .header("x-requested-with", "XMLHttpRequest")
                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .execute()) {
            String body = execute.body();
            JSON parse = JSONUtil.parse(body);
            String public_key = parse.getByPath("data[0][0]").toString();
            String file = ResourceUtil.getResource("key/public.key").getFile();
            FileWriter.create(new File(file)).write(public_key, false);
            String private_key = parse.getByPath("data[0][1]").toString();
            String file2 = ResourceUtil.getResource("key/private.key").getFile();
            FileWriter.create(new File(file2)).write(private_key, false);
        }
    }
}
