package config;


import manytomany.entity.Actor;
import manytomany.entity.Role;
import onetomany.entity.Contact;
import onetomany.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class SessionFactoryConfig {
    public static SessionFactory buildSessionFactory(){
        return new Configuration()
                .setProperty(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/java")
                .setProperty(Environment.JAKARTA_JDBC_USER, "postgres")
                .setProperty(Environment.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(Environment.SHOW_SQL, "true")
                .setProperty(Environment.HBM2DDL_AUTO, "create")
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Role.class)
                .buildSessionFactory();
    }
}
