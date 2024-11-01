package entity.seminar5;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="humans", schema = "seminar5")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @OneToOne (fetch = FetchType.LAZY, mappedBy = "human")
    private Car car;
}
