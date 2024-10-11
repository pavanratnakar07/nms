package com.techeazy.login.Service;

import com.techeazy.login.Entity.Email;
import com.techeazy.login.Repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
;

@Service
public class SendinblueEmailService {

    private final RestTemplate restTemplate;
    private final EmailRepository emailRepository;
  

    @Autowired
    public SendinblueEmailService(RestTemplate restTemplate , EmailRepository emailRepository) {
        this.restTemplate = restTemplate;
        this.emailRepository = emailRepository;
    }


    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    // New method to filter emails by status
    public List<Email> getEmailsByStatus(Email.EmailStatus status) {
        return emailRepository.findByStatus(status);
    }

    public Page<Email> getEmails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return emailRepository.findAll(pageable);
    }

    public void email(){

    }


    public void sendEmail(Email email) {

        email.setStatus(Email.EmailStatus.PENDING); // Set initial status
        email.setSentTime(null); // Reset sent time before sending
        emailRepository.save(email); // Save the email to the database

        String url = "https://api.sendinblue.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("api-key", API_KEY);

        String jsonBody = String.format("{\"sender\":{\"name\":\"Pavan Ratnakar\",\"email\":\"your-email@example.com\"}," +
                        "\"to\":[{\"email\":\"%s\"}],\"subject\":\"%s\",\"htmlContent\":\"%s\"}",
                email.getTo(), email.getSubject(), email.getBody());

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            email.setStatus(Email.EmailStatus.SENT);
            email.setSentTime(LocalDateTime.now()); // Set the sent time
        } else {
            email.setStatus(Email.EmailStatus.FAILED);
        }

        emailRepository.save(email); // Update the email status and time in the database
    }
}
