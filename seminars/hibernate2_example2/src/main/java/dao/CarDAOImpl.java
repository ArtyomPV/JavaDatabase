package dao;

import entity.Car;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CarDAOImpl implements CarDAO{
    private SessionFactory sessionFactory;
    public CarDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Car> getAllCars() {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> carQuery = session.createQuery("from Car", Car.class);
            return carQuery.list();
        }
    }


    @Override
    public Integer addCar(Car car) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(car);
            Integer id = car.getId();
            System.out.println(id);
            transaction.commit();
            return id;
        }
    }

    @Override
    public Car getCarById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Car.class, id);
        }

    }

    @Override
    public void deleteCarById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Car carById = getCarById(id);
            Transaction transaction = session.beginTransaction();
            session.remove(carById);
            transaction.commit();
        }

    }

    @Override
    public void updateCarById(Human human) {
        try (Session session = sessionFactory.openSession()) {

        }
    }
}
