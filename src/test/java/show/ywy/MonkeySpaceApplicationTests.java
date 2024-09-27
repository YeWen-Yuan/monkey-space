package show.ywy;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ResourceUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import show.ywy.entity.SecureTool;

import javax.annotation.Resource;
import java.net.URL;

@SpringBootTest
class MonkeySpaceApplicationTests {

    @Test
    void contextLoads() {
        String encrypt = SecureTool.encrypt("123");
        System.out.println(encrypt);
        System.out.println(SecureTool.decrypt(encrypt));
    }

}
