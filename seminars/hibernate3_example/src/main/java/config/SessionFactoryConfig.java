package config;

import entity.Boat;
import entity.Car;
import entity.Human;
import entity.Worker;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;



public class SessionFactoryConfig {
    private static SessionFactoryConfig instance;
    private final SessionFactory sessionFactory;
    private SessionFactoryConfig() {
        sessionFactory = build();
    }

    public static SessionFactory getSessionFactory(){
            if(instance == null){
                instance =  new SessionFactoryConfig();
            }
            return instance.sessionFactory;
        }

    private SessionFactory build(){
        return new Configuration()
                .addAnnotatedClass(Human.class)
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Boat.class)
                .addAnnotatedClass(Worker.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(AvailableSettings.SHOW_SQL, "true")
                .setProperty(AvailableSettings.HBM2DDL_AUTO, "update")
                .buildSessionFactory();
    }
}
