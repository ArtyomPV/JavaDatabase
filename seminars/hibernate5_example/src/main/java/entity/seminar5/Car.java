package entity.seminar5;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cars", schema = "seminar5")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String model;
    private Integer year;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Human human;
}
