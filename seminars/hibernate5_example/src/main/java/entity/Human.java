package entity;

import converter.Gender1Converter;
import jakarta.persistence.*;
import listener.HumanEventListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.TrueFalseConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="human", schema = "seminar4")
@EntityListeners(value = HumanEventListener.class)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    BigDecimal balance;
    @Column(name = "name")
    String humanName;

    @OneToMany
    @JoinColumn(name="human_id")
    List<Car> cars;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Convert(converter = Gender1Converter.class)
    private Gender1 gender1;

    @Column(name = "is_active")
    private Boolean isActive;
    @Convert(converter = TrueFalseConverter.class)
    @Column(name = "is_married")
    private Boolean isMarried;

    private LocalDate birthday;
// добавить зависимость jackson-databind
    @Column(name = "string_map")
    @JdbcTypeCode( SqlTypes.JSON )
    private Map<String, String> stringMap;

    // @CreationTimestamp(source = SourceType.VM) //используется класс HumanEventListener
    @Column(name = "created_date")
    private LocalDate createDate;

    @Column(name = "updated_date")
    @UpdateTimestamp(source = SourceType.VM)
    private LocalDate updateDate;


//    @PreUpdate
//    public void setLastUpdate(Human h){
//        h.setUpdateDate(LocalDate.now());
//    }
}
