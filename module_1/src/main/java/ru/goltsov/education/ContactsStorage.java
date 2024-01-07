package ru.goltsov.education;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;

public abstract class ContactsStorage {
    protected List<String> contacts;
    @Value("${app.contacts-file}")
    private String contactsFilePath;

    abstract List<String> getContacts();

    abstract boolean saveContact(String contact);

    abstract boolean removeContact(String contact);

    boolean saveFile() throws Exception {

        try {
            File file = new File(contactsFilePath);
            if (file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                    for (String contact : contacts) {
                        writer.write(contact);
                        writer.write("\n");
                    }
                    return true;
                }
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            throw new FileNotFoundException("Файл для сохранения контактов не найден");
        }
    }
}
