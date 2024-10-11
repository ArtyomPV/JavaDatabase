package lesson3.saveObjectToDB.services;

import lesson3.saveObjectToDB.model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetAllEmployees {
    public static List<Employee> getAllEmployees(Connection connection){
        List<Employee> employees = new ArrayList<>();
        try(Statement statement = connection.createStatement();

        ) {
            ResultSet resultSet = statement.executeQuery("select * from employee");
            while(resultSet.next()){
                employees.add(Employee.builder()
                        .name(resultSet.getString("name"))
                        .occupation(resultSet.getString("occupation"))
                        .salary(resultSet.getInt("salary"))
                        .joinDate(resultSet.getObject("join_date", LocalDate.class))
                        .build());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }
}
