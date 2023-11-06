package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("org.example")
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${app.charsetName}")
    private String charsetName ;

    public String getCharsetName() {
        return charsetName;
    }
}
