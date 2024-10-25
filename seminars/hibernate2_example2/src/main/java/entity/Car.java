package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars", schema = "exampleh")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String carName;
    @ManyToOne
    @ToString.Exclude
    private Human human;
}
