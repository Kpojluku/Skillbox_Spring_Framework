package ru.goltsov.education.config;

import ru.goltsov.education.ContactsStorage;
import ru.goltsov.education.DefaultContactsStorage;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application-dev.properties")
@Profile("dev")
public class DefaultAppConfig {

    @Bean
    public ContactsStorage defaultContactsProcedure() {
        return new DefaultContactsStorage();
    }

}
