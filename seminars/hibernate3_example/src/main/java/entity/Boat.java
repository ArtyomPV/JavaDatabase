package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OptimisticLock;

import java.time.LocalDate;

@Entity
@Table(name = "boats", schema = "hibernate_seminar3")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Boat {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @OptimisticLock(excluded = true) // когда часто обновляется поле можно исключить из версионности
    private String name;
    @Version
    private Long version;
    @Column(name="update_date")
    private LocalDate date;
    @ManyToOne
    @ToString.Exclude
    private Worker worker;

}
