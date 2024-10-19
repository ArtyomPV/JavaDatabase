package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="human", schema="hibernate_3")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Human {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    @OneToMany
    private List<Car> cars;
}
