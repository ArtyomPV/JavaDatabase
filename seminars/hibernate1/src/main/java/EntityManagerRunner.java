import config.SessionFactoryConfig;
import entity.Human;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;

public class EntityManagerRunner {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactoryConfiguration();

        EntityManager entityManager = sessionFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Query query = entityManager.createQuery("delete from Human h where h.id=9");
        query.executeUpdate();
        transaction.commit();


        Human human = entityManager.find(Human.class, 6);
        System.out.println(human);
    }
}
