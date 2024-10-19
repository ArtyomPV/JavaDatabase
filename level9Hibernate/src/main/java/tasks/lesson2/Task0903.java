package tasks.lesson2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.Property;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Properties;

public class Task0903 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/test");
        properties.put(Environment.JAKARTA_JDBC_USER, "root");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "root");

        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(Animal.class)
                .setProperties(properties)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        Query<Animal> fromAnimal = session.createQuery("select a from Animal a", Animal.class);
        List<Animal> resultList = fromAnimal.getResultList();
        System.out.println(resultList);
    }
}
