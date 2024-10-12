import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {
//        firstAttempt();
        secondAttempt();
//        saveHumanExample();
    }

    private static void secondAttempt() {
        // Создание SessionFactory
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        // Открытие сессии
        Session session = factory.openSession();

        try {
            // Начало транзакции
            session.beginTransaction();

            // Создание объекта Human
            Human human = new Human();
            human.setName("Семён");
            human.setAge(42);

            // Сохранение объекта Human
            session.persist(human);

            // Завершение транзакции
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Откат при ошибке
            }
            e.printStackTrace();
        } finally {
            // Закрытие сессии
            session.close();
            factory.close(); // Закрытие SessionFactory
        }
    }
    private static void firstAttempt() {
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();


//        Transaction transaction = session.getTransaction();

        Human human = Human.builder().name("Gena").age(25).build();
        System.out.println(human);
        try{

            session.beginTransaction();
            session.save(human);
            session.getTransaction().commit();

        } catch (Exception e){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
            sessionFactory.close();
        }


    }
    public static void saveHumanExample() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        try {
//            session.beginTransaction();
            Human human = new Human();
            human.setName("Тест");
            human.setAge(25);
            session.persist(human);
            Human human1 = session.get(Human.class, 1);
            System.out.println(human1);

//            session.getTransaction().commit();
        } catch (Exception e) {
//            if (session.getTransaction() != null) {
//                session.getTransaction().rollback();

//            }
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
