package ch.devprojects.cms.repository;

import ch.devprojects.cms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for Role entity.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a Role by its name.
     * @param name the name of the role
     * @return an Optional containing the Role if found, or empty if not found
     */
    Optional<Role> findByName(String name);
}