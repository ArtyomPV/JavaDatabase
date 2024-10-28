package entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.Type;


import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "hibernate4_mapping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "name")
    String name;
    @Column(name = "level")
    Integer level;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "favorite_color")
    Color favoriteColor;
    // Данное поле должно хранить дату в виде строки
    @Column(name = "created_date")
    public LocalDate createdDate;


}
