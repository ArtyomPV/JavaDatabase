package one_to_many.task1304.config;


import one_to_many.task1304.entity.Author;
import one_to_many.task1304.entity.Book;
import one_to_many.task1304.entity.Publisher;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class MySessionFactory {
    private final SessionFactory sessionFactory;
    private static MySessionFactory instance;
    private MySessionFactory(){
    Properties properties = new Properties();
        properties.put(Environment.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.put(Environment.JAKARTA_JDBC_USER, "postgres");
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, "root");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put("hibernate.connection.useUnicode", true);
        properties.put("hibernate.connection.characterEncoding", "UTF-8");
        properties.put("hibernate.connection.charSet", "UTF-8");
        properties.put(Environment.HBM2DDL_AUTO, "none");


        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Publisher.class)
                .buildSessionFactory();
}
    public static SessionFactory getSessionFactory(){
        if(instance == null){
            instance = new MySessionFactory();
        }
        return instance.sessionFactory;
    }
}
