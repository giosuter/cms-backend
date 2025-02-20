package ch.devprojects.cms.controller;

import java.util.List;
import java.util.stream.Collectors;

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


/**
 * 
 * @RestController → Marks this as a REST API controller.
 * @RequestMapping("/contacts") → Base URL for API endpoints.
 * @GetMapping, @PostMapping, @PutMapping, @DeleteMapping → Handles HTTP requests.
 * 
 */

/**
 * Defines REST API endpoints for CRUD operations.
 * Uses constructor injection for better maintainability.
 * Handles not found cases properly.	
 */

/**
 * Uses DTO for cleaner responses.
 * @RequestBody for JSON request parsing.
 * @ResponseEntity for HTTP status handling.
 */

/**
 * Exposes the API.
 */

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
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact(
                contactDTO.getFirstname(), contactDTO.getLastname(),
                contactDTO.getEmail(), contactDTO.getPhone());
        Contact savedContact = contactService.createContact(contact);
        return ResponseEntity.ok(new ContactDTO(
                savedContact.getId(), savedContact.getFirstname(),
                savedContact.getLastname(), savedContact.getEmail(), savedContact.getPhone()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
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
        return ResponseEntity.noContent().build();
    }
}