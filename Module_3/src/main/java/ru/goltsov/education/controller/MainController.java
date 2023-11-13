package ru.goltsov.education.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.goltsov.education.dto.Contact;
import ru.goltsov.education.service.ContactService;

import java.util.Optional;

@Controller
@Slf4j
public class MainController {

    private final ContactService contactService;

    @Autowired
    public MainController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/addContact")
    public String getAddContact(Model model) {
        model.addAttribute("contacts", contactService.getListContact());
        model.addAttribute("contact", new Contact());
        return "index";
    }

    @PostMapping("/addContact")
    public String postAddContact(Model model, Contact contact) {
        contactService.addContact(contact);
        return "redirect:/addContact";
    }

    @PostMapping("/editContact")
    public String editContact(@RequestParam("id") int id, Model model) {
        Optional<Contact> contactById = contactService.getContactById(id);
        if (contactById.isPresent()) {
            model.addAttribute("contact", contactById.get());
            return "editContact";
        } else {
            log.info("Id контакта не найден: " + id);
            return "redirect:/addContact";
        }
    }

    @PostMapping("/saveEditedContact")
    public String saveEditedContact(Model model, Contact contact) {
        contactService.editContact(contact);
        return "redirect:/addContact";
    }

    @PostMapping("/deleteContact")
    public String deleteContact(@RequestParam("id") int id, Model model) {
        if (contactService.removeContact(id)) {
            log.info("Контакт удалён");
        } else log.info("Id контакта не найден: " + id);

        return "redirect:/addContact";
    }
}
