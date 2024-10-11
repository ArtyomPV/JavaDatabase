package lesson3.saveObjectToDB;

import lesson3.saveObjectToDB.model.Employee;
import lesson3.saveObjectToDB.services.GetAllEmployees;
import lesson3.saveObjectToDB.services.GetEmployee;
import lesson3.saveObjectToDB.services.UploadEmployee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RunnerApp {
    private final String DB_URL = "jdbc:mysql://localhost:3306/javarush";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "root";
    public static void main(String[] args) throws SQLException {

//        imageLoad();
      employeeLoad();

      int id = 2;
      employeeGetById(id);
      employeeGetAll();

    }

    private static void employeeGetAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush", "root", "root")) {
            List<Employee> employees = GetAllEmployees.getAllEmployees(connection);
            employees.forEach(System.out::println);

        } catch (SQLException e){
            e.printStackTrace();
        }
        }

    private static void employeeGetById(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush", "root", "root")) {

        Employee employee = GetEmployee.getEmployeeById(connection, id);
            System.out.println(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void employeeLoad() throws SQLException {
        Employee valera = new Employee("Dima", "tester", 1000, LocalDate.now());
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush", "root", "root")) {
            UploadEmployee.addEmployee(connection, valera);
        }
    }

    private static void imageLoad() {
        String filePath = "level8JDBC/src/main/resources/images/images.jpg";
        String fileName = "Kitty";
        ImageUploader imageUploader = new ImageUploader();
        imageUploader.load(filePath, fileName);


    }
}
