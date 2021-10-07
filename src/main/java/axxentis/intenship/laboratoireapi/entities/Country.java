package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYS_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "indicative", updatable = true, nullable = false, length = 5, unique = true)
    private String indicative;

    @Column(name = "code_iso", updatable = true, nullable = false, length = 3)
    private String isoCode;

    @Column(name = "nom", updatable = true, nullable = false, length = 80)
    private String libelle;

    @JsonBackReference
    @OneToMany(targetEntity = City.class, mappedBy = "country", fetch = FetchType.LAZY)
    private List<City> cities = new ArrayList<>();
}
