package com.techeazy.login.Repository;

import com.techeazy.login.Entity.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findByStatus(Email.EmailStatus status);

    Page<Email> findAll(Pageable pageable); // Method for pagination
}
