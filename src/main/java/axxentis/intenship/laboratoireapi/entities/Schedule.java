package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_schedule")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // // use to correct Infinite Recursion
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SCHEDULE_ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Schedule Title cannot be empty or Null")
    private String title;

    @Column(columnDefinition="Weekly tasks", nullable = false)
    private String task;

    private String startDate;

    private String finishDate;

    private Long percentageComplete;

    private Boolean scheduleStatus;

    private String observation;

    private Long hrResult;

    private Long supervisorResult;

    private String hrObservation;

    private String supervisorObservation;

    private Boolean scheduleComplete;

    // Relationships
    // not getting the use of the annotations below
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference()
    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CONTACT_ID")
    private Employee employee;

    @OneToMany(targetEntity = Task.class, mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();



}
