package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name="animal")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal {
    private String name;
    private String age;
}
