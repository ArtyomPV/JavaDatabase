package lesson2.connectToDatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal {
    private String name;
    private String age;
}
