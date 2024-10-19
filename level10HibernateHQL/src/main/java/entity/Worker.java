package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="worker_data", schema="level10_hibernate")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer salary;
    @OneToMany
    private List<WorkerTask> workerTask;
    @Column(name="join_date")
    private LocalDate joinDate;
}
