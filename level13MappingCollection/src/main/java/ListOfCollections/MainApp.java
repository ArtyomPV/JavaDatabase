package ListOfCollections;

import ListOfCollections.config.SessionFactoryConfig;
import ListOfCollections.entity.User;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();


        sessionFactory.inTransaction(session -> {
            List<String> answers = new ArrayList<>(List.of("yes", "No"));
            User user = new User();
            user.setName("Artyom");
            user.setAnswers(answers);
            session.persist(user);
        });

        sessionFactory.inTransaction(session -> {
            User user = session.find(User.class, 1);
            System.out.println(user.getName() + ": ");
            user.answers.forEach(System.out::println);
        });

    }

}
