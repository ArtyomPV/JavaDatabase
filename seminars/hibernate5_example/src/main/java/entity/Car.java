package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars", schema="seminar4")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    String carName;

    private Integer date;
    @ManyToOne
    @ToString.Exclude
    private Human human;
}
