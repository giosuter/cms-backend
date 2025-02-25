package ch.devprojects.cms.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;  // Import for validation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.devprojects.cms.dto.ContactDTO;
import ch.devprojects.cms.model.Contact;
import ch.devprojects.cms.service.ContactService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactDTO> getAllContacts() {
        return contactService.getAllContacts().stream()
                .map(contact -> new ContactDTO(
                        contact.getId(), contact.getFirstname(), contact.getLastname(),
                        contact.getEmail(), contact.getPhone()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        return contactService.getContactById(id)
                .map(contact -> ResponseEntity.ok(new ContactDTO(
                        contact.getId(), contact.getFirstname(), contact.getLastname(),
                        contact.getEmail(), contact.getPhone())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact(
                contactDTO.getFirstname(), contactDTO.getLastname(),
                contactDTO.getEmail(), contactDTO.getPhone());
        Contact savedContact = contactService.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED) // Set status to 201 Created
                .body(new ContactDTO(
                        savedContact.getId(), savedContact.getFirstname(),
                        savedContact.getLastname(), savedContact.getEmail(), savedContact.getPhone()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @Valid @RequestBody ContactDTO contactDTO) {
        Contact updatedContact = new Contact(
                contactDTO.getFirstname(), contactDTO.getLastname(),
                contactDTO.getEmail(), contactDTO.getPhone());
        Contact savedContact = contactService.updateContact(id, updatedContact);
        return ResponseEntity.ok(new ContactDTO(
                savedContact.getId(), savedContact.getFirstname(),
                savedContact.getLastname(), savedContact.getEmail(), savedContact.getPhone()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build(); // Set status to 204 No Content
    }
}