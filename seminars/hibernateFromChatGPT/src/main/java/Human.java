import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="human", schema="exampleh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;
}
