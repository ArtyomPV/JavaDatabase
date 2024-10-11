package lesson3.saveObjectToDB.services;

import lesson3.saveObjectToDB.model.Employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;

public class UploadEmployee {

    public static boolean addEmployee(Connection connection, Employee employee) throws SQLException {
        // Создаем и подготавливаем запрос на вставку данных в таблицу
        String insertQuery = "insert into employee(name, occupation, salary, join_date) values(?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        // Заполняем запрос данными из объекта Employee
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getOccupation());
        statement.setInt(3, employee.getSalary());
        statement.setDate(4, Date.valueOf(employee.getJoinDate()));

        int rowCount = statement.executeUpdate();

        return rowCount > 0;
    }
}
