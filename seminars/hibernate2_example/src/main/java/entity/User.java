package entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="user_data")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="user_name")
    private String name;
    @Column(name="user_level")
    private Integer level;

    @Column(name="user_created")
    private Date created;
}
