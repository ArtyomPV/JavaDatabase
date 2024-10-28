import config.SessionFactoryConfig;
import entity.Square;
import org.hibernate.SessionFactory;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        sessionFactory.inTransaction(session -> {
            Square sq = Square.builder().width(5).height(2).build();
            session.persist(sq);
        });
        sessionFactory.inTransaction(session -> {
            Square square = session.find(Square.class, 1);
            System.out.println(square);

        });
    }
}
