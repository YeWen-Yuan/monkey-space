package show.ywy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import show.ywy.entity.SecureTool;

@SpringBootApplication
public class MonkeySpaceApplication {

    public static void main(String[] args) {
        // create local key
        SecureTool.createKey();
        SpringApplication.run(MonkeySpaceApplication.class, args);
    }

}
