package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // // use to correct Infinite Recursion
@Table(name = "task")
public class Task extends  Common{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "title", updatable = true, nullable = false)
    private String title;

    @Column(columnDefinition="TEXT", nullable = true)
    private String description;

    // Relationships
    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "EMPLOYEE_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Employee employee;

}
