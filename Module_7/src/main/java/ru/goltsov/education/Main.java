package ru.goltsov.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        BlockHound.install(); // vm options: -XX:+AllowRedefinitionToAddDeleteMethods
        ReactorDebugAgent.init();
        SpringApplication.run(Main.class, args);
    }

}
