package lesson3.saveObjectToDB.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@ToString
public class Employee {
    String name;
    String occupation;
    int salary;
    LocalDate joinDate;
}
