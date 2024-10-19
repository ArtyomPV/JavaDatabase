package tasks.lesson2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="animal_table", schema="test")
public class Animal {
    @Id
    Integer id;
    @Column(name="name")
    String name;
    @Column(name="age")
    Integer age;
    @Column(name="family")
    String family;

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", family='" + family + '\'' +
                '}';
    }
}
