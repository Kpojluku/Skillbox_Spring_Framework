package ru.goltsov.education;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InitContactsStorage extends ContactsStorage {
    @Value("${app.contacts-init-file}")
    private String initFileName;

    public InitContactsStorage() {
    }

    @PostConstruct
    public void init() {
        contacts = readLinesFromFile(initFileName);
    }

    public List<String> readLinesFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public List<String> getContacts() {
        return contacts;
    }

    @Override
    public boolean saveContact(String contact) {
        return contacts.add(contact);
    }

    @Override
    public boolean removeContact(String email) {
        List<String> contactsForRemove = contacts.stream().filter(o -> o.contains(email)).toList();
        return contacts.removeAll(contactsForRemove);
    }

}
