import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.SessionFactoryConfig;
import entity.Car;
import entity.Gender;
import entity.Gender1;
import entity.Human;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactory();

//        createCarsAndHumans(sessionFactory);
//
//        setGenderToHuman(sessionFactory);
//        createHumanAlla(sessionFactory);
//
//        workWithBooleanField(sessionFactory);
//        getHumanWithFieldBirthday(sessionFactory);
//        System.out.println("============================");
//        getHumanAndSeializeToJson(sessionFactory);

//        getHumanAlla(sessionFactory);
//        addHumanWithCreatedDate(sessionFactory);
//        useUpdatedDateToAlla(sessionFactory);
        createHumanPetr(sessionFactory);
    }

    /** Создаем human с использованием аннотации @PrePersist с использованием HumanEventListener
     * @param sessionFactory
     */
    private static void createHumanPetr(SessionFactory sessionFactory)
    {
        sessionFactory.inTransaction(session -> {
            Human petr = Human.builder().humanName("Petr").gender1(Gender1.MALE).balance(BigDecimal.valueOf(300.0)).build();
            session.persist(petr);
        });
    }

    private static void useUpdatedDateToAlla(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            String hql = "from Human where humanName = :name";
                Human alla = session.createQuery(hql, Human.class)
                        .setParameter("name", "Alla")
                        .getSingleResult();
                alla.setBalance(BigDecimal.valueOf(250));
                session.merge(alla);
        });
    }

    private static void addHumanWithCreatedDate(SessionFactory sessionFactory) {
        Human human = Human.builder()
                .humanName("Semen")
                .gender(Gender.MALE)
                .gender1(Gender1.MALE)
                .birthday(LocalDate.of(1998, 11, 10))
                .isMarried(false)
                .isActive(true)
                .stringMap(Map.of("city", "Lipesk"))
                .build();
        sessionFactory.inTransaction(session -> session.persist(human));
    }

    private static void getHumanAlla(SessionFactory sessionFactory) {
        String hql = "from Human where humanName = :name";
        sessionFactory.inTransaction(session -> {
            Human alla = session.createQuery(hql, Human.class)
                    .setParameter("name", "Alla")
                    .getSingleResult();
            System.out.println(alla);
        });
    }

    private static void getHumanAndSeializeToJson(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Human human = session.find(Human.class, 3);

            human.setStringMap(Map.of("City", "Kaliningrad"));


        });
    }

    private static void getHumanWithFieldBirthday(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            System.out.println(session.find(Human.class, 3));
        });
    }

    private static void workWithBooleanField(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Human human = session.createQuery("from Human h where h.humanName = :name", Human.class)
                    .setParameter("name", "Alla")
                    .getSingleResult();

            human.setIsActive(true);
            System.out.println(human);
        });
        sessionFactory.inTransaction(session -> {
            Human alla = session.createQuery("from Human where humanName = :name", Human.class)
                    .setParameter("name", "Alla")
                    .uniqueResult();
            alla.setIsMarried(true);
            System.out.println(alla);
        });
    }

    private static void setGenderToHuman(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {

            Human human = session.find(Human.class, 1);
            human.setGender1(Gender1.
                    MALE);
            System.out.println(human);
        });
    }

    private static void createHumanAlla(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Human alla = Human.builder().humanName("Alla").gender1(Gender1.FEMALE).balance(BigDecimal.valueOf(300.0)).build();
            session.persist(alla);
        });
    }

    private static void createCarsAndHumans(SessionFactory sessionFactory) {
        Human valera = Human.builder().humanName("Valera")
                .gender(Gender.MALE)
                .gender1(Gender1.MALE)
                .balance(BigDecimal.valueOf(200))
                .build();
        Human boris = Human.builder()
                .humanName("Boris")
                .gender1(Gender1.MALE)
                .balance(BigDecimal.valueOf(300))
                .build();

        Car volvo = Car.builder().carName("Volvo").date(2007).build();
        Car mazda = Car.builder().carName("Mazda").date(2012).build();
        Car toyota = Car.builder().carName("Toyota").date(2018).build();

        sessionFactory.inTransaction(session -> {
            session.persist(valera);
            session.persist(boris);
        });

        sessionFactory.inTransaction(session -> {
            session.persist(volvo);
            session.persist(mazda);
            session.persist(toyota);
        });

        sessionFactory.inTransaction(session -> {
            session.find(Car.class, 1).setHuman(valera);
            session.find(Human.class, 2).getCars().add(mazda);
            session.find(Human.class, 1).getCars().add(toyota);
        });


        sessionFactory.inTransaction(session -> {
            List<Human> fromHuman = session.createQuery("from Human", Human.class).list();
            System.out.println(fromHuman);
        });
    }
}
