package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Autorisation extends Common{

    @Id
    @Column(name = "AUTORISATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean statut;
    @ManyToOne(targetEntity = Profil.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PROFIL_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Profil profil;
    @ManyToOne(targetEntity = Privilege.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "PRIVILEGE_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Privilege privilege;
}
