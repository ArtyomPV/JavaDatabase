package lesson3.saveObjectToDB.model;

import lombok.*;


@NoArgsConstructor
@Builder
@ToString
@Getter
public class Worker {
    private String name;
    private int age;
    private String occupation;


    public Worker(String name, int age, String occupation) {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
    }
}
