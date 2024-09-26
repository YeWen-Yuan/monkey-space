package show.ywy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yzs
 */
@Configuration
public class WebConfig {

    // 解决跨域问题
     @Bean
     public WebMvcConfigurer corsConfigurer() {
         return new WebMvcConfigurer() {
             @Override
             public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/**")
                         .allowedOriginPatterns("*")
                         .allowedMethods("GET", "POST", "PUT", "DELETE")
                         .allowCredentials(true)
                         .maxAge(3600);
             }
         };
     }

}
