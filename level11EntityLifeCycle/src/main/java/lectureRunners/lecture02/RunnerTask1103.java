package lectureRunners.lecture02;

import config.SessionFactoryConfig;
import entity.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*

Попробуй достать из БД объект класса Animal с id + 1. Используй методы get(), load() и find() объекта session.
Результат запиши в поля animalGet, animalLoad и animalFind соответственно. Запусти программу и убедись, что значения полей animalGet и animalFind равны null, а значение animalLoad не равно null.

Требования:
•	В методе main у объекта класса Session должен быть вызван метод get с параметрами Animal.class и id+1.
•	В методе main у объекта класса Session должен быть вызван метод load с параметрами Animal.class и id+1.
•	В методе main у объекта класса Session должен быть вызван метод find с параметрами Animal.class и id+1.
•	После работы программы значения полей animalGet и animalFind должны быть равны null, а значение animalLoad - не равно null.
 */
public class RunnerTask1103 {
    public static Animal animalGet;
    public static Animal animalLoad;
    public static Animal animalFind;
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        Animal murka = Animal.builder()
                .name("Murka")
                .age(2)
                .family("cats")
                .build();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(murka);
            System.out.println(id);
//            session.persist(murka);
            animalGet = session.get(Animal.class, 1);
            animalLoad = session.load(Animal.class, 1);
            animalFind = session.find(Animal.class, 1);
            transaction.commit();
        }

        System.out.println(animalGet == null);
        System.out.println(animalLoad != null);
        System.out.println(animalFind == null);



    }
}
