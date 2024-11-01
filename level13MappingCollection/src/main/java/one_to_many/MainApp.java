package one_to_many;

import one_to_many.config.SessionFactoryConfig;
import one_to_many.entity.Employee;
import one_to_many.entity.Task;
import org.hibernate.SessionFactory;

public class MainApp {
    public static void main(String[] args) {
        Task task1 = Task.builder().name("Do smth. important").build();
        Task task2 = Task.builder().name("Have a rest").build();

        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();

        sessionFactory.inTransaction(session -> {
            session.persist(task1);
            session.persist(task2);
        });

        sessionFactory.inTransaction(session -> {
            Employee director = session.find(Employee.class, 4);
            director.getTasks().add(task1);
            director.getTasks().add(task2);
        });
    }
}
