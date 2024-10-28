package entity.lecture1;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="new_user", schema = "hibernate4_mapping")
public class NewUserWithEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;
    @Embedded
    private UserAddress userAddress;
    @Column(name = "created_date")
    private LocalDate createdDate;
}
