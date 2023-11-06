package ru.goltsov.education.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.goltsov.education.StudentRegister;

@ComponentScan("ru.goltsov.education")
@Configuration
@PropertySource("classpath:application.yaml")
public class AppConfig {

    @Value("${myapp.studentInit.path:default_value}")
    private String filePath;

    @Bean
    @ConditionalOnProperty(prefix = "myapp.studentInit", name = "enabled", havingValue = "true")
    public StudentRegister studentRegisterInit() {
        return new StudentRegister(filePath);
    }

    @Bean
    @ConditionalOnProperty(prefix = "myapp.studentInit", name = "enabled", havingValue = "false", matchIfMissing = true)
    public StudentRegister studentRegister() {
        return new StudentRegister();
    }

}
