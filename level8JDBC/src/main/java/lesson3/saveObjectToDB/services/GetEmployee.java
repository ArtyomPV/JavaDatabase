package lesson3.saveObjectToDB.services;

import lesson3.saveObjectToDB.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class GetEmployee {
    public static Employee getEmployeeById(Connection connection, int id) throws SQLException {
        String sql  = "Select * from employee where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

//        if(!resultSet.first() ) return null;

        return Employee.builder()
                .name(resultSet.getString("name"))
                .occupation(resultSet.getString("occupation"))
                .salary(resultSet.getInt("salary"))
                .joinDate(resultSet.getObject("join_date", LocalDate.class))
                .build();

    }
}
