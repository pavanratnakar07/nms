package com.techeazy.login.Controller;


import com.techeazy.login.Entity.Email;
import com.techeazy.login.Service.SendinblueEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final SendinblueEmailService sendinblueEmailService;

    @Autowired
    public EmailController(SendinblueEmailService sendinblueEmailService) {
        this.sendinblueEmailService = sendinblueEmailService;
    }


    @GetMapping("/all/emails") // Endpoint to fetch emails
    public ResponseEntity<List<Email>> getAllEmails() {
        List<Email> emails = sendinblueEmailService.getAllEmails();
        return ResponseEntity.ok(emails);
    }

    // New endpoint to fetch emails by status
    @GetMapping("/emails/status") // Example: /email/emails/status?status=SENT
    public ResponseEntity<List<Email>> getEmailsByStatus(@RequestParam Email.EmailStatus status) {
        List<Email> emails = sendinblueEmailService.getEmailsByStatus(status);
        return ResponseEntity.ok(emails);
    }

    @GetMapping("/emails/pages")
    public ResponseEntity<Page<Email>> getEmails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Email> emails = sendinblueEmailService.getEmails(page, size);
        return ResponseEntity.ok(emails);
    }


    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        sendinblueEmailService.sendEmail(email);
        return "Email sent successfully";
    }
}
