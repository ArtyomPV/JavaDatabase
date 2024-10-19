package config;

import entity.Car;
import entity.Human;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class SessionFactoryConfig {

    public static SessionFactory buildSessionFactory() {
        return new Configuration()
                .addAnnotatedClass(Human.class)
                .addAnnotatedClass(Car.class)
                .setProperty(Environment.JAKARTA_JDBC_URL, "jdbc:mysql://localhost:3306/hibernate_3")
                .setProperty(Environment.JAKARTA_JDBC_USER, "root")
                .setProperty(Environment.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(Environment.SHOW_SQL, "true")
                .setProperty(Environment.HBM2DDL_AUTO, "update")
                .buildSessionFactory();
    }
}
