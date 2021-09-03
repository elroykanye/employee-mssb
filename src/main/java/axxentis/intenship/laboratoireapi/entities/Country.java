package axxentis.intenship.laboratoireapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "iso_code", updatable = true, nullable = true)
    private String iso_code;

    @Column(name = "indicative", updatable = true, nullable = true)
    private String indicative;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date created_at;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updated_on;

}
