package ru.prusov.jbdcexample.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Human {
    Integer id;
    String name;
    Integer balance;
}
