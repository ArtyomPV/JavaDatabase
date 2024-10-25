package config;

import entity.Car;
import entity.Human;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfig {
    public SessionFactory buildSessionFactory(){
        return new Configuration()
                .addAnnotatedClass(Human.class)
                .addAnnotatedClass(Car.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(AvailableSettings.HBM2DDL_AUTO, "validate")
                .setProperty(AvailableSettings.SHOW_SQL, true)
                .setProperty(AvailableSettings.USE_SQL_COMMENTS, true)
                .buildSessionFactory();

    }
}
