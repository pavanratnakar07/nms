package comTecheazy.Controller;

import comTecheazy.Entity.Email;
import comTecheazy.Service.SendinblueEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @PostMapping("/send")
    public String sendEmail(@RequestBody Email email) {
        sendinblueEmailService.sendEmail(email);
        return "Email sent successfully";
    }
}
