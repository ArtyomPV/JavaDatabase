package config;

import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.internal.MetadataImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryConfig {

    public static SessionFactory buildSessionFactory(){
        try{
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            Metadata metadata = new MetadataSources(standardServiceRegistry)
                    .getMetadataBuilder()
                    .build();
            return metadata.buildSessionFactory();
        } catch (HibernateError error){
            throw new HibernateError("Error creating session factory");
        }
    }
}
