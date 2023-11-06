package org.example.config;

import org.example.ContactsStorage;
import org.example.InitContactsStorage;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {

    @Bean
    public ContactsStorage initContactsProcedure() {
        return new InitContactsStorage();
    }

}
