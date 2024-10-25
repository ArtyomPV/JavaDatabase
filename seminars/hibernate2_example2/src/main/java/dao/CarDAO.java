package dao;

import entity.Car;
import entity.Human;

import java.util.List;

public interface CarDAO {
    public List<Car> getAllCars();
    public Integer addCar(Car car);
    public Car getCarById(Integer id);

    public void deleteCarById(Integer id);

    public void updateCarById(Human human);
}
