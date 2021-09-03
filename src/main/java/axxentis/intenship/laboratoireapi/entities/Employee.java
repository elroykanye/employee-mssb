package axxentis.intenship.laboratoireapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "firstName", updatable = true,nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", updatable = true, nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", updatable = true, nullable = false, length = 200)
    private String email;

    @Column(name = "gender", updatable = true, nullable = false, length = 20)
    private String gender;


}
