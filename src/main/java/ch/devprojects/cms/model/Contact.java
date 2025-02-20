package ch.devprojects.cms.model;

import jakarta.persistence.*;

/**
  	•	@Entity → Marks the class as a database entity.
	•	@Table(name = "contacts") → Maps to the contacts table in MySQL.
	•	@Id @GeneratedValue → Auto-generates unique id.
	•	@Column(nullable = false, unique = true) → Ensures required fields.
 */

/**
 * Defines a Contact entity mapped to the contacts table.
 * Uses explicit constructors, getters, and setters (no Lombok).
 * @Id @GeneratedValue ensures auto-incrementing id.
 */

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    // Constructors
    public Contact() {
    }

    public Contact(String firstname, String lastname, String email, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}