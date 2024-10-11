package domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Car {
    Integer id;
    String name;
    Integer year;

}
