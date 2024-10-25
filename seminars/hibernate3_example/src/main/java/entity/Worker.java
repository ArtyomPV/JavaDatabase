package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "worker", schema = "hibernate_seminar3")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Worker {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;

    @OneToMany
    @JoinColumn(name = "worker_id")
    private List<Boat> boat;
}

