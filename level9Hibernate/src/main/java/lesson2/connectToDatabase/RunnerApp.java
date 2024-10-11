package lesson2.connectToDatabase;

import lesson2.connectToDatabase.entity.Human;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class RunnerApp {
    public static void main(String[] args) {
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.init();
        List<Human> allEmployees = employeeManager.getAllEmployees();
        allEmployees.forEach(System.out::println);

    }
}
