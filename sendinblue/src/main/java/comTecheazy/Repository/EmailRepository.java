package comTecheazy.Repository;

import comTecheazy.Entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findByStatus(Email.EmailStatus status);
}
