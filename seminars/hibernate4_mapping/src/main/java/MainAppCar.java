import config.SessionFactoryConfig;
import entity.Car;
import entity.Human;
import org.hibernate.SessionFactory;

public class MainAppCar {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
//example1(sessionFactory);
example2(sessionFactory);


    }

    private static void example2(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Human human = session.find(Human.class, 1);
            Car volvo = Car.builder().name("volvo").human(human).build();
            session.persist(volvo);
        });
    }

    private static void example1(SessionFactory sessionFactory) {
        //        Car peugout = Car.builder().name("Peugout").build();
        // в поле entity сущности добавлена аннотация @CreationTimestamp(source = SourceType.VM),
        // которая автоматически добавляет текущую дату
        sessionFactory.inTransaction(session -> {
//            session.persist(peugout);
            Car peugout = session.find(Car.class, 3);
            Human human = session.find(Human.class, 1);
            peugout.setName("Peugout Partner");
            peugout.setHuman(human);
            session.merge(peugout);

        });
    }
}
