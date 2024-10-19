import config.MySessionFactory;
import config.SessionFactoryConfig;
import entity.Employee;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service.EmployeeService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class RunnerApp {
    public static void main(String[] args) {

//        task01();
        task02();


    }

    private static void task02() {
//        SessionFactory sessionFactory = MySessionFactory.getSessionFactory();
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        EmployeeService employeeService = new EmployeeService(sessionFactory);

        Employee victor = Employee.builder()
                .name("Victor")
                .age(29)
                .smth("cleaner")
                .build();

        Employee valera = Employee.builder()
                .name("Valera")
                .age(32)
                .smth("teacher")
                .build();

        Employee vasya = Employee.builder()
                .name("Vasia")
                .age(47)
                .smth("developer")
                .build();

        employeeService.addEmployee(victor);
        employeeService.addEmployee(valera);
        employeeService.addEmployee(vasya);

        List<Employee> developers = employeeService.findEmployeesBySmth("developer");
        developers.forEach(System.out::println);

        employeeService.UpdateEmployeesByIdMutationQuery("java developer", 1);
//        employeeService.promoteAll();



    }

    private static void task01() {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();

        Session session = sessionFactory.openSession();

        User petr = User.builder()
                .name("Petr")
                .level(12)
                .created(LocalDate.now())
                .build();



        Transaction transaction = session.beginTransaction();
        session.persist(petr);
        transaction.commit();
    }
}
