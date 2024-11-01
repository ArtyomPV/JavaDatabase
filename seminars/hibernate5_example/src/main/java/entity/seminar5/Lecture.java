package entity.seminar5;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lectures", schema = "seminar5")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String lectureName;
}
