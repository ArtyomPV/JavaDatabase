package one_to_many.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee", schema = "hibernate13_OneToMany")
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
    @OneToMany(cascade = CascadeType.ALL)
            @JoinColumn(name = "employee_id")
    Set<Task> tasks = new HashSet<>();
}
