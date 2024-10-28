package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "human", schema = "exampleh")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    @OneToMany
    @JoinColumn(name="human_id")
    List<Car> cars;
    @Enumerated
    private Gender gender;
}
