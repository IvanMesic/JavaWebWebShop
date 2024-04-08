package hr.meske.javaWeb.repo;

import hr.meske.javaWeb.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<User, Long> {

    Optional<User> findByName(String username);
}

