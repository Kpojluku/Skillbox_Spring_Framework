package org.example.config;

import org.example.ContactsStorage;
import org.example.DefaultContactsStorage;
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
