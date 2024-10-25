import config.SessionFactoryConfig;
import entity.Gender;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        Human valera = createHuman("Valera", 25, 50.25, Gender.MALE);
        Human alla = createHuman("Alla", 32, 17.0, Gender.FEMALE);

        BigDecimal balance = valera.getBalance();
        System.out.println(balance.setScale(0, RoundingMode.CEILING));


//        sessionFactory.inTransaction(session -> session.persist(valera));
        sessionFactory.inTransaction(session -> {Human human = session.find(Human.class, 1);
        human.setIsActive(false);
        human.setBirthday(LocalDate.of(2000,04,25));
            System.out.println(human);
        });

//        sessionFactory.inTransaction(session -> session.persist(alla));
        sessionFactory.inTransaction(session -> {Human human = session.find(Human.class, 2);
            human.setIsActive(true);
            System.out.println(human);
        });


    }

    private static Human createHuman(String name, Integer age, Double balance, Gender gender) {
        return Human.builder()
                .name(name)
                .age(age)
                .balance(BigDecimal.valueOf(balance))
                .gender(gender)
                .build();
    }
}
