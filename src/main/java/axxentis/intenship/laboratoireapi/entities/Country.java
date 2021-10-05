package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // // use to correct Infinite Recursion
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "COUNTRY_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "iso_code", updatable = true, nullable = true)
    private String isoCode;

    @Column(name = "indicative", updatable = true, nullable = true)
    private String indicative;

    // Relationships
    @JsonBackReference
    @OneToMany(targetEntity = City.class, mappedBy = "country", fetch = FetchType.LAZY)
    private List<City> cities;

}
