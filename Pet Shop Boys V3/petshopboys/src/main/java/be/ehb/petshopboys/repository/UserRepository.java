package be.ehb.petshopboys.repository;

import be.ehb.petshopboys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This is the repository for the users. It extends JpaRepository, which contains methods for basic CRUD operations.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
