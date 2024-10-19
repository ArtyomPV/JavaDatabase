package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cars", schema="hibernate_3")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String carName;

    @ManyToOne
    @JoinColumn(name = "human_id")
    @ToString.Exclude
    private Human human;

}
