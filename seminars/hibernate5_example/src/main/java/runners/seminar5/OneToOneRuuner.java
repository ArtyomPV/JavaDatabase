package runners.seminar5;

import config.SessionFactoryConfig;
import entity.seminar5.Car;
import entity.seminar5.Human;
import org.hibernate.SessionFactory;

public class OneToOneRuuner {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactory("simenar5/hibernate.cfg.xml");

        Human valera = Human.builder()
                .name("Valera")
                .build();

        Car bmw = Car.builder()
                .model("BMW")
                .year(1998)
                .human(valera)
                .build();


        sessionFactory.inTransaction(session -> {
            session.persist(bmw);
        });
    }
}
