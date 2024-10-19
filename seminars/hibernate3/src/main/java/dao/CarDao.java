package dao;

import entity.Car;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CarDao {
    private SessionFactory sessionFactory;

    public CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCar(Car car) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(car);
            transaction.commit();
        }
    }

    public List<Car> getAllCars(){
        try (Session session = sessionFactory.openSession()) {
            Query<Car> queryCar = session.createQuery("from Car c", Car.class);
            return queryCar.list();
        }
    }

    public Car getHumanCar(Integer id){
        try (Session session = sessionFactory.openSession()) {
            return session.get(Car.class, id);
        }
    }
}
