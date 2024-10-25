package entity;

import converter.GenderConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.type.TrueFalseConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "humans", schema = "hibernate4_mapping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer age;
    BigDecimal balance; //float, Float, double, Double
    LocalDate birthday;
    @Convert(converter = GenderConverter.class)
    Gender gender;
    @Convert(converter = TrueFalseConverter.class)
@Column(name = "is_active")
    Boolean isActive;

    @OneToMany
    List<Car> cars;


}
