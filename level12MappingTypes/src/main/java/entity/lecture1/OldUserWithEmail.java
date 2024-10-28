package entity.lecture1;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="old_user", schema = "hibernate4_mapping")
public class OldUserWithEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="address_country")
    private String country;
    @Column(name="address_city")
    private String city;
    @Column(name="address_street")
    private String street;
    @Column(name="address_home")
    private String home;
    @Column(name = "created_date")
    private LocalDate createdDate;
}
