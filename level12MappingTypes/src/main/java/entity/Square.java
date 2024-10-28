package entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "square", schema = "hibernate4_mapping")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Square {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer width;
    Integer height;
    // данное поле не будет помещено в БД
    @Transient
    Integer total;

    @Formula(value = " width * height ")
    Integer square;


}
