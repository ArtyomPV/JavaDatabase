package entity.lecture1;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class UserAddress {
    @Column(name="address_country")
    private String country;
    @Column(name="address_city")
    private String city;
    @Column(name="address_street")
    private String street;
    @Column(name="address_home")
    private String home;
}
