package show.ywy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author yzs
 */
@Configuration
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {
    private Set<String> code;


}
