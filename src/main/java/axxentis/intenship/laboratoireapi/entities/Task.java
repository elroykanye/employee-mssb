package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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

    @Column(nullable = false)
    private Boolean taskComplete;

    private String assigneeEmail;

    // Relationships
    @ManyToOne(targetEntity = Schedule.class, fetch = FetchType.LAZY)
    // TODO 1. check the @JoinColumn fix which was originally the commented line below
    // @JoinColumn(nullable = false, name = "EMPLOYEE_ID")
    @JoinColumn(nullable = false, name = "SCHEDULE_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Schedule schedule;

}
