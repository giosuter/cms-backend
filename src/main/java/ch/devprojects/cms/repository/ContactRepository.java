package ch.devprojects.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.devprojects.cms.model.Contact;


/**
 * JpaRepository<Contact, Long> → Provides basic CRUD operations.
 * @Repository → Marks it as a Spring Data repository.
 */

/**
 * Provides built-in CRUD operations using JpaRepository.
 * No need to write SQL queries!
 */

/**
 *	JpaRepository<Contact, Long> provides built-in CRUD operations.
 *	findByEmail(String email) allows searching contacts by email.
 */

/**
 * The repository layer interacts with the database using Spring Data JPA.
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	Optional<Contact> findByFirstname(String firstname);
	Optional<Contact> findByLastname(String lastname);
    Optional<Contact> findByEmail(String email);
    // TODO: eventually remove findByPhone (must be formatted?)
    Optional<Contact> findByPhone(String phone);
}