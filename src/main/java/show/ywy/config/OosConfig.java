package show.ywy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzs
 */
@Configuration
@ConfigurationProperties(prefix = "oos")
@Data
public class OosConfig {
    private String endpoint;
    private String bucketName;
    private String OSS_ACCESS_KEY_ID;
    private String OSS_ACCESS_KEY_SECRET;
}
