package org.example;

import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ContactProcedure contactProcedure = context.getBean(ContactProcedure.class);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, context.getBean(AppConfig.class).getCharsetName()))) {
            while (true) {
                System.out.println("Введите текст:");

                String inputText = reader.readLine();
                String processedText = contactProcedure.contactProcessing(inputText);
                if ("--exit".equals(inputText)) break;

                System.out.println(processedText);
            }
        } catch (IOException e) {
            System.err.println("Ошибка обработки: " + e.getMessage());
            e.printStackTrace();
        }
    }
}