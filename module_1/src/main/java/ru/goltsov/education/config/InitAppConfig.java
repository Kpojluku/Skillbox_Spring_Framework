package ru.goltsov.education.config;

import ru.goltsov.education.ContactsStorage;
import ru.goltsov.education.InitContactsStorage;
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
