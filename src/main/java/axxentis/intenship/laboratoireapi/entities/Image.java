package axxentis.intenship.laboratoireapi.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "url", updatable = true,nullable = false)
    private String url;

    //Relationship
    @OneToOne(optional = false)
    // TODO 2. check the @JoinColumn fix which was originally the commented line below
    // @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @JoinColumn(name = "employee_id", referencedColumnName = "CONTACT_ID")
    // @JsonBackReference
    private Employee employee;

}
