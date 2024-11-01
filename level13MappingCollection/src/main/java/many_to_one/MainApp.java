package many_to_one;


import many_to_one.config.SessionFactoryConfig;
import many_to_one.entity.Employee;
import many_to_one.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();

//        getAllEmployees(sessionFactory);
        getAllTaskFromEmployeeByName(sessionFactory, "Иванов Иван");
        System.out.println("----------------------------");
        getAllEmployeesWithNotDoneTask(sessionFactory);

        assignTaskToDirector(sessionFactory);



    }

    private static void assignTaskToDirector(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Employee director = session.find(Employee.class, 4);
            String hql = "update Task set employee = :user where employee is null";
            Query query = session.createQuery(hql);
            query.setParameter("user", director);
            query.executeUpdate();
        });
    }

    private static void getAllEmployeesWithNotDoneTask(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        String hql = "select distinct employee from Task where deadline < current_date";
        Query<Employee> sessionQuery = session.createQuery(hql, Employee.class);
        List<Employee> employees = sessionQuery.list();

        employees.forEach(System.out::println);
    }

    private static void getAllTaskFromEmployeeByName(SessionFactory sessionFactory, String name) {
        Session session = sessionFactory.openSession();
        String hql = "from Task where employee.name = :name";
        Query<Task> query = session.createQuery(hql, Task.class);
        query.setParameter("name", name);
        List<Task> list = query.list();

        list.forEach(System.out::println);
        session.close();
    }

    private static void getAllEmployees(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            String hql = "from Employee";
            Query<Employee> sessionQuery = session.createQuery(hql, Employee.class);
            List<Employee> list = sessionQuery.list();

            list.forEach(System.out::println);
            session.close();
        });
    }
}
