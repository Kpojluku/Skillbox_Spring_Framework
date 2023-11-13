package ru.goltsov.education.repository;

import ru.goltsov.education.dto.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    void addContact(Contact contact);
    List<Contact> getContactList();
    Optional<Contact> getContactById(int id);
    boolean editContact(Contact contact);
    boolean removeContact(int id);

}
