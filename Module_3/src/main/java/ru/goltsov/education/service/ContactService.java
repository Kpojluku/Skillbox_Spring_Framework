package ru.goltsov.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.goltsov.education.dto.Contact;
import ru.goltsov.education.repository.ContactRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void addContact(Contact contact) {
        contact.setId(generateNewId(contactRepository.getContactList()));
        contactRepository.addContact(contact);
    }

    public List<Contact> getListContact() {
        return contactRepository.getContactList();
    }

    public Optional<Contact> getContactById(int id) {
        return contactRepository.getContactById(id);
    }

    public boolean editContact(Contact contact) {
        return contactRepository.editContact(contact);
    }

    public boolean removeContact(int Id) {
        return contactRepository.removeContact(Id);
    }

    private static int generateNewId(List<Contact> contactList) {
        return contactList.stream()
                .max(Comparator.comparingInt(Contact::getId))
                .map(o -> o.getId() + 1).orElse(0);
    }
}
