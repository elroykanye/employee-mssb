package axxentis.intenship.laboratoireapi.entities;

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
public class Privilege extends Common{

    @Id
    @Column (name = "PRIVILEGE_ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    @OneToMany(targetEntity = Autorisation.class, mappedBy = "privilege", fetch = FetchType.LAZY)
    private List <Autorisation> autorisations = new ArrayList <> ();

}
