package lesson3.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Properties;

public class AnimalManager {
    private SessionFactory sessionFactory;

    public AnimalManager() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/test");
        properties.put(Environment.JAKARTA_JDBC_USER, "root");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "root");
        properties.put(Environment.SHOW_SQL, "root");
//        properties.put(Environment.HBM2DDL_AUTO, "update");

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Animal.class)
                .buildSessionFactory();
    }

    public List<Animal> getAllAnimals() {
        try (Session session = sessionFactory.openSession()) {
            Query<Animal> fromAnimal = session.createQuery("from Animal", Animal.class);
            return fromAnimal.list();
        }
    }

    public void addAnimal(Animal animal){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(animal);
            transaction.commit();
        }
    }

    public void removeAnimal(Animal animal){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(animal);
            transaction.commit();
        }
    }
}
