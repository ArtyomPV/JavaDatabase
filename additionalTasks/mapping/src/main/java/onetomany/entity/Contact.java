package onetomany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contacts", schema="mapping")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String data;
    /*
    Контакт не должен иметь пользователя, пользователь должен иметь не сколько контактов
    @ManyToOne
    private User user;
     */
}
