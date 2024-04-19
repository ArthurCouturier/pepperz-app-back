package cout.dev.projetcuisine.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://pepperz-back.vercel.app",
                                "https://www.pepperz.app",
                                "https://pepperz.app",
                                "http://pepperz-back.vercel.app",
                                "http://www.pepperz.app",
                                "http://pepperz.app",
                                "http://localhost"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }
        };
    }
}
