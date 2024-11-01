package many_to_many.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee", schema = "hibernate13_ManyToMany")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String occupation;
    Integer salary;
    Integer age;
    @Column(name = "join_date")
    LocalDate joinDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_task", schema = "hibernate13_ManyToMany",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))

    Set<Task> tasks = new HashSet<>();
}