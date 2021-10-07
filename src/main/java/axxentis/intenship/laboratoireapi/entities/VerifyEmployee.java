package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VerifyEmployee extends Common{

    @Id
    @Column (name = "VERIFIER_CONTACT_ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String telephone;
    @Column(nullable = true)
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat (pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dateEmission;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat (pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dateExpiration;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat (pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dateVerify;
    private Boolean statut;
    @ToString.Exclude
    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CONTACT_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Employee employee;
}
