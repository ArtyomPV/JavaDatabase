package config;

import entity.Square;
import entity.User;
import entity.lecture1.NewUserWithEmail;
import entity.lecture1.OldUserWithEmail;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


public class SessionFactoryConfig {
public static SessionFactory buildSessionFactory(){
        return new Configuration()
                .setProperty(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                .setProperty(Environment.JAKARTA_JDBC_USER, "postgres")
                .setProperty(Environment.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(Environment.SHOW_SQL, "true")
                .setProperty(Environment.HBM2DDL_AUTO, "create")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Square.class)
                .addAnnotatedClass(NewUserWithEmail.class)
                .addAnnotatedClass(OldUserWithEmail.class)
                .buildSessionFactory();
    }
}
