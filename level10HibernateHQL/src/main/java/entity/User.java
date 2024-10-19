package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="user_data", schema="level10_hibernate")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_name")
    private String name;
    @Column(name="user_level")
    private Integer level;

    @Column(name="user_created")
    private LocalDate created;
}
