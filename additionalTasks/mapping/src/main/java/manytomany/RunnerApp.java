package manytomany;

import config.SessionFactoryConfig;
import org.hibernate.SessionFactory;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
    }
}
