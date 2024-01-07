package ru.goltsov.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactProcedure {

    private static final String CONTACT_PATTERN = "[а-яёa-z]+ [а-яёa-z]+ [а-яёa-z]+;\\+\\d{11};\\S+@\\S+";
    private static final String EMAIL_PATTERN = "[^;\\s]+@[^;\\s]+";
    private ContactsStorage contactsStorage;

    @Autowired
    public ContactProcedure(ContactsStorage contactsStorage) {
        this.contactsStorage = contactsStorage;
    }


    public String contactProcessing(String inputText) {
        String result;
        if (inputText.startsWith("--remove")) {
            String email = inputText.replaceFirst("--remove ", "");
            if (email.matches(EMAIL_PATTERN)) {
                result = contactsStorage.removeContact(email) ? "Контакт успешно удалён" : "Контакт не найден";
            } else {
                return "Для удаления контакта введите email";
            }
        } else if (inputText.startsWith("--view")) {
            List<String> contacts = contactsStorage.getContacts();
            return contacts.stream().map(o -> o.replaceAll(";", " | ")).collect(Collectors.joining("\n"));
        } else if (inputText.toLowerCase().trim().matches(CONTACT_PATTERN)) {
            return contactsStorage.saveContact(inputText) ? "Успешно сохранен" : "Ошибка сохранения";
        } else if ("--save".equals(inputText) || "--exit".equals(inputText)) {
            try {
                return contactsStorage.saveFile()  ? "Файл успешно сохранен" : "Ошибка сохранения";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "Ошибка ввода";
        }

        return result;
    }


}
