package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_data", schema = "level11_entity_life")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String name;
}
