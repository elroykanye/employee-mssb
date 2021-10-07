package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VILLE_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "libelle", updatable = true, nullable = false, length = 80)
    private String libelle;


    @ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PAYS_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Country country;

    @JsonBackReference
    @OneToMany(targetEntity = Employee.class, mappedBy = "city", fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();
}
