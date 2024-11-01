package many_to_many;

import many_to_many.config.SessionFactoryConfig;
import many_to_many.entity.Employee;
import many_to_many.entity.Task;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
            addTasks(sessionFactory);
            addEmployees(sessionFactory);

            assignTaskToEmployee(sessionFactory);

            getAllTaskFromEmployees(sessionFactory);



    }

    private static void getAllTaskFromEmployees(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
            employees.stream()
                    .forEach(employee -> {
                        employee.getTasks().stream()
                                .flatMap(task -> task.getEmployees().stream())
                                .forEach(System.out::println);
                                });
                    });

    }

    private static void assignTaskToEmployee(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
            List<Task> tasks = session.createQuery("from Task", Task.class).list();

            employees.get(0).getTasks().add(tasks.get(1));
            tasks.get(0).getEmployees().add(employees.get(1));

        });
    }

    private static void addEmployees(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Employee employee1 = new Employee().builder()
                    .name("Valera")
                    .age(17).salary(1000)
                    .joinDate(LocalDate.of(2022, 10, 12))
                    .occupation("developer")
                    .build();
            Employee employee2 = new Employee().builder()
                    .name("Petr")
                    .age(25).salary(1500)
                    .joinDate(LocalDate.of(2021, 7, 25))
                    .occupation("developer")
                    .build();
            session.persist(employee1);
            session.persist(employee2);
        });
    }

    private static void addTasks(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Task findNewJob = new Task().builder().name("Find new job").build();
            Task visitTheDoctor = new Task().builder().name("visit the doctor").build();
            session.persist(findNewJob);
            session.persist(visitTheDoctor);
        });
    }
}
