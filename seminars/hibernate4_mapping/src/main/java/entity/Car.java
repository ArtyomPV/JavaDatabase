package entity;

import entity.listener.CarEventListener;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "cars", schema = "hibernate4_mapping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(value = CarEventListener.class)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "car_name")
    String name;

    @ManyToOne
    @ToString.Exclude
    Human human;

//    @CreationTimestamp(source = SourceType.VM)
    @Column(name = "created_date")
    LocalDate createdDATE;

    @UpdateTimestamp
    @Column(name="updated_date")
    LocalDate updatedDate;





}
