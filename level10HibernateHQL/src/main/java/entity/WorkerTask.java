package entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ValueGenerationType;

import java.time.LocalDate;

@Entity
@Table(name="task_data", schema="level10_hibernate")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkerTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    @ToString.Exclude
    private Worker worker;
    private LocalDate deadline;
}
