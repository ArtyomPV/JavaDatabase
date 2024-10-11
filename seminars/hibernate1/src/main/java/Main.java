import config.SessionFactoryConfig;
import entity.Animal;
import entity.Human;
import lesson3.saveObjectToDB.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        try(SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactoryConfiguration()){
            Session session = sessionFactory.openSession();
           Human valera =  Human.builder().name("Valera").age(30).build();
           Human vasya =  Human.builder().name("Vasya").age(30).build();
//           session.save(valera);
           session.save(vasya);


            Human human = session.get(Human.class, 4);
            System.out.println(human);

            System.out.println("finished");
        }
    }
}
