package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends Common {

    @Id
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String lastName;
    private String firstName;
    private Boolean statut;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private Boolean onLine;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dateLastConnection;

    @Column(columnDefinition = "integer default 0")
    private Integer nombreGenerationCode;
    @Column(columnDefinition = "integer default 0")
    private Integer nombreVerifyCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dateLastVerifyCode;


    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "DEPARTEMENT_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Department department;


    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CITY_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private City city;

    @OneToMany(targetEntity = PhoneNumber.class, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    @OneToMany(targetEntity = EmployeeProfil.class, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeeProfil> employeeProfils = new ArrayList<>();

    @OneToMany(targetEntity = Schedule.class, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

}
