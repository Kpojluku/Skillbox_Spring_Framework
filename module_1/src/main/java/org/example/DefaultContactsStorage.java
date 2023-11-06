package org.example;

import java.util.ArrayList;
import java.util.List;

public class DefaultContactsStorage extends ContactsStorage {

    public DefaultContactsStorage() {
        contacts = new ArrayList<>();
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
