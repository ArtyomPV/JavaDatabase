import config.SessionFactoryConfig;
import entity.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RunnerAppStates {
    public static void main(String[] args) {

        SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();

        Session session = sessionFactory.openSession();

        Car geely = new Car().builder()
                .name("Geely")
                .build();
        Transaction transaction = session.beginTransaction();
        session.persist(geely);   // делает insert
        geely.setName("Renault"); // делает update
        transaction.commit();     // сохраняет в БД
        geely.setName("Peugeot");  // не сохраняет в БД так как состояние detached
        session.close();

        System.out.println(geely); // выводит объект с именем "Peugeot"
    }
}
