package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "firstName", updatable = true, nullable = false)
    private String firstName;

    @Column(name = "lastName", updatable = true, nullable = false)
    private String lastName;

    @Column(name = "email", updatable = true, nullable = true)
    private String email;

    @Column(name = "gender", updatable = true, nullable = true)
    private String gender;

    private String password;

    // Relatinship
    @OneToOne(mappedBy = "employee")
//    @JsonManagedReference
    private Image image;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Task> tasks;


    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "DEPARTEMENT_ID")
     @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Department department;


    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CITY_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private City city;

    @OneToMany(targetEntity = PhoneNumber.class, mappedBy = "employee", fetch = FetchType.LAZY)
   private List <PhoneNumber> phoneNumbers = new ArrayList<>();

    @OneToMany(targetEntity = EmployeeProfil.class, mappedBy = "employee", fetch = FetchType.LAZY)
    private List <EmployeeProfil> employeeProfils = new ArrayList <> ();


}
