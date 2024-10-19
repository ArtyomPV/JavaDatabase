package config;

import entity.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class MySessionFactory {
    private static MySessionFactory mySessionFactory;
    private SessionFactory sessionFactory;

    private MySessionFactory(){
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver");
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/level10_hibernate");
        properties.put(Environment.JAKARTA_JDBC_USER, "postgres");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "root");
        properties.put(Environment.SHOW_SQL, "true");

        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){
        if(mySessionFactory == null) {
            mySessionFactory = new MySessionFactory();
        }
        return mySessionFactory.sessionFactory;
    }
}
