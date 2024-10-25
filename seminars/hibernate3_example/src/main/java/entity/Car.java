package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Table(name = "cars", schema = "hibernate_seminar3")
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
public class Car {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Integer id;
        private String name;
}
