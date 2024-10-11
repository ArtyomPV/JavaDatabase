package domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Human  {
    Integer id;
    String name;
    List<Car> cars = new ArrayList<>();
}
