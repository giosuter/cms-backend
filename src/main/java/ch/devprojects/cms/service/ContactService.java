package ch.devprojects.cms.service;

import ch.devprojects.cms.model.Contact;
import ch.devprojects.cms.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Handles CRUD operations for Contact entities.
 * Uses Optional<Contact> to avoid null pointer exceptions.
 * Throws an exception when updating or deleting a non-existing contact.
 */
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Retrieves all contacts from the repository.
     * @return a list of contacts.
     */
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    /**
     * Retrieves a contact by its ID.
     * @param id the ID of the contact to retrieve.
     * @return an Optional containing the found contact or empty if not found.
     */
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    /**
     * Creates a new contact in the repository.
     * @param contact the contact entity to create.
     * @return the created contact entity.
     */
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    /**
     * Updates an existing contact identified by its ID.
     * @param id the ID of the contact to update.
     * @param contactDetails the new contact details.
     * @return the updated contact entity.
     */
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

    /**
     * Deletes a contact by its ID.
     * Throws an exception if the contact is not found.
     * @param id the ID of the contact to delete.
     */
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with ID: " + id);
        }
        contactRepository.deleteById(id);
    }
}
