package entity.lecture3.task1203;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;

public class Solution {

    public static void main(String[] args) {
        TimeClass timeClass = new TimeClass();

        timeClass.setId(1L);
        timeClass.setInstant(Instant.ofEpochSecond(10));
        timeClass.setZonedDateTime(ZonedDateTime.now(ZoneId.systemDefault()));

        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(timeClass);
            session.getTransaction().commit();
        }
    }

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.USER, "postgres");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "create");

        SessionFactory sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(TimeClass.class)
                .buildSessionFactory();

        return sessionFactory;
    }
}