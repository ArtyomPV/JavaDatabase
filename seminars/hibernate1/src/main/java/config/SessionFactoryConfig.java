package config;

import entity.Human;
import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfig {
    public SessionFactory buildSessionFactory(){
        try{
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateError error)
        {
            throw new HibernateError("Error creating session factory");
        }
    }

    public SessionFactory buildSessionFactoryConfiguration(){
        return new Configuration()
                .addAnnotatedClass(entity.Human.class)
                // PostgreSQL
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(AvailableSettings.SHOW_SQL, true)
                .buildSessionFactory();
    }
}
