import config.SessionFactoryConfig;
import entity.Boat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;

public class RunnerAppLock {
/*
select * from table for update - пессимистическая блокировка

 */
public static void main(String[] args) {
    SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();
    Session session = sessionFactory.openSession();
    addBoat(session);
    // При выполнении этого метода изменяется версия записи
    updateNameBoat(session);
    Session session1 = sessionFactory.openSession();
    Session session2 = sessionFactory.openSession();
    updateBoatWithConflict(session1, session2);
//    updateDateBoat(session);
    updateBoatWithConflictInThread(session1, session2);






    session.close();
}

    private static void updateDateBoat(Session session) {
        Boat boat = session.find(Boat.class, 1);
        Transaction transaction = session.beginTransaction();
        boat.setDate(LocalDate.now());
        transaction.commit();
    }

    private static void updateBoatWithConflictInThread(Session session1, Session session2) {
    Thread thread1 = new Thread(()->{
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){

        }
        Boat boat1 = session1.find(Boat.class, 1);
        Transaction transaction1 = session1.beginTransaction();
        boat1.setName("Victory");
        transaction1.commit();

    });


    Thread thread2 = new Thread(()->{
        Boat boat2 = session2.find(Boat.class, 1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        Transaction transaction2 = session2.beginTransaction();
        boat2.setName("Crushing");

        transaction2.commit();

    });
    thread1.start();
    thread2.start();
    }

    private static void updateBoatWithConflict(Session session1, Session session2) {
        Boat boat1 = session1.find(Boat.class, 1);
        Boat boat2 = session2.find(Boat.class, 1);

        Transaction transaction1 = session1.beginTransaction();
        boat1.setName("Victory");

        Transaction transaction2 = session2.beginTransaction();
        boat2.setName("Crushing");

        transaction2.commit();
        transaction1.commit();

        session1.close();
        session2.close();
    }

    private static void updateNameBoat(Session session) {
        Boat boat = session.find(Boat.class, 1);
        boat.setName("Крузенштерн");
        Transaction transaction = session.beginTransaction();
        session.persist(boat);
        transaction.commit();
    }

    private static void addBoat(Session session) {
        // hibernate создает version автоматически
        Boat pobeda = Boat.builder().name("Pobeda").date(LocalDate.of(2024, 10, 10)).build();
        Transaction transaction = session.beginTransaction();
        session.persist(pobeda);
        transaction.commit();

    }

}
