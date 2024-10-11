package comTecheazy.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "`to`")
    private String to;
    private String subject;
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private LocalDateTime sentTime;


   public enum EmailStatus {
        PENDING,
        SENT,
        FAILED
    }
}
