package ch.devprojects.cms.service;

import ch.devprojects.cms.model.Contact;
import ch.devprojects.cms.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements CRUD operations explicitly (no Lombok!).
 * Uses Optional to handle missing contacts safely.
 * Uses @Autowired constructor injection instead of field injection.
 */

/**
 * Handles CRUD operations.
 * Uses Optional<Contact> to avoid null pointer exceptions.
 * Throws an exception when updating or deleting a non-existing contact.
 */

/**
 * Encapsulates business logic.
 */
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id, Contact contactDetails) {
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if (contactOptional.isPresent()) {
            Contact contact = contactOptional.get();
            contact.setFirstname(contactDetails.getFirstname());
            contact.setLastname(contactDetails.getLastname());
            contact.setEmail(contactDetails.getEmail());
            contact.setPhone(contactDetails.getPhone());
            return contactRepository.save(contact);
        } else {
            throw new RuntimeException("Contact not found");
        }
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }
}