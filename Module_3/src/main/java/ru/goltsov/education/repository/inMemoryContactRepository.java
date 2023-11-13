package ru.goltsov.education.repository;

import org.springframework.stereotype.Repository;
import ru.goltsov.education.dto.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class inMemoryContactRepository implements ContactRepository{

    private final List<Contact> contactList = new ArrayList<>();

    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public Optional<Contact> getContactById(int id){
        return contactList.stream().filter(o -> o.getId() == id).findAny();
    }

    public boolean editContact(Contact contactForChange) {
        Integer id = null;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId() == contactForChange.getId()) {
                id = i;
                break;
            }
        }
        if (id != null) {
            contactList.set(id, contactForChange);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeContact(int id) {
        return contactList.removeIf(contact -> contact.getId() == id);
    }

}
