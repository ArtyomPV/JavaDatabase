package dto;

import entity.Car;

import java.util.List;

public record HumanDTO(Integer age,
                       String name,
                       List<Car> cars) {
}
