package ListOfCollections.config;

import ListOfCollections.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfig {

    public static SessionFactory buildSessionFactory(){
        return new Configuration()
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "postgres")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "root")
                .setProperty(AvailableSettings.SHOW_SQL, "true")
                .setProperty(AvailableSettings.HBM2DDL_AUTO, "update")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}
