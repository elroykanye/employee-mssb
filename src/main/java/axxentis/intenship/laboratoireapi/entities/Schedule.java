package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // // use to correct Infinite Recursion
@Table(name = "schedule")
public class Schedule extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Schedule Title cannot be empty or Null")
    @Column(name = "title", updatable = true)
    private String title;
    @Column(columnDefinition="Weekly tasks", nullable = true)
    private String task;
    private String startDate;
    private String finishDate;
    private Long percentageComplete;
    private Boolean status;
    private String observation;
    private Long HR_result;
    private Long supervisor_result;
    private String HR_observation;
    private String supervisor_observation;



    // Relationships
    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CONTACT_ID")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Employee employee;

    @OneToMany(targetEntity = Task.class, mappedBy = "schedule", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();



}
