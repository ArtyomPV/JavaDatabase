package entity.seminar5;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books", schema = "seminar5")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String bookName;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST )
    private Student student;
}
