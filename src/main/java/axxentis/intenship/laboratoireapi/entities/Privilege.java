package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // // use to correct Infinite Recursion
@Table(name = "privilege")
public class Privilege extends Common{
    @Id
    @Column (name = "PRIVILEGE_ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    @OneToMany(targetEntity = Autorisation.class, mappedBy = "privilege", fetch = FetchType.LAZY)
    private List<Autorisation> autorisations = new ArrayList<>();

}
