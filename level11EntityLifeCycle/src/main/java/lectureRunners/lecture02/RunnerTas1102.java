package lectureRunners.lecture02;

import config.SessionFactoryConfig;
import entity.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
/*

Сохрани объект animalCat в БД с помощью метода save объекта session,
потом вызови метод saveOrUpdate для объектов animalCat и animalDog.
Запусти программу и убедись, что в БД содержится только две записи
(метод saveOrUpdate для объекта animalDog отработал как save, а для объекта animalCat — как update).

Требования:
•	В методе main у объекта класса Session должен быть вызван метод save с нужным параметром.
•	В методе main у объекта класса Session должен быть вызван метод saveOrUpdate для двух разных объектов.
 */
public class RunnerTas1102 {
    public static void main(String[] args) throws Exception {
        Animal animalCat = Animal.builder().build();
        animalCat.setName("Murka");
        animalCat.setAge(5);
        animalCat.setFamily("Cats");

        Animal animalDog = Animal.builder().build();
        animalDog.setName("Barsik");
        animalDog.setAge(3);
        animalDog.setFamily("Dogs");

        try {
            SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            //напишите тут ваш код
            session.save(animalCat);
            session.saveOrUpdate(animalDog);
            session.saveOrUpdate(animalCat);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
