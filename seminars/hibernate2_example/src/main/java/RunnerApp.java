import config.SessionFactoryConfig;
import org.hibernate.SessionFactory;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactory();

    }
}
