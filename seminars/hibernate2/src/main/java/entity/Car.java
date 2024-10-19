package entity;

import jakarta.persistence.*;

@Entity
@Table (name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="human_id")
    private Human human;
}
