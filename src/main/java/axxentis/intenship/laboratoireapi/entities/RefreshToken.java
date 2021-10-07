package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 03/08/2021
 * Time: 17:24
 *
 * @mail: krolshelby@gmail.com.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RefreshToken extends Common {
    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "CONTACT_ID")
    private Employee employee;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

}
