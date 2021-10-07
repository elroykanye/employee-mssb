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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // // use to correct Infinite Recursion
@Table(name = "department")
public class Department extends Common{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTEMENT_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "libelle", updatable = true, nullable = false)
    private String libelle;

    @Column(columnDefinition="TEXT", nullable = true)
    private String description;

    @JsonBackReference
    @OneToMany(targetEntity = Employee.class, mappedBy = "department", fetch = FetchType.LAZY)
    private List <Employee> employees = new ArrayList<>();

}
