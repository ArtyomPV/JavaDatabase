package many_to_one.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "task", schema = "hibernate13_manyToOne")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;
    //    @Column(name = "employee_id")
    //    Integer employeeId;
    String name;
    LocalDate deadline;

}
