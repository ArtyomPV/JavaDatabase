package lesson2.preparementStatement;

import java.sql.*;

public class PreparedStatementStatementSample {
    public static void run() {
        String firstName = "Harry";
        String lastName = "Potter";
        String phone = "+7123456789";
        String email = "test@test.com";


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush", "root", "root");

            //Запрос с указанием мест для параметров в виде символа "?"
            String sql = "INSERT INTO contact(first_name, last_name, phone, email) VALUES (?, ?, ?, ?);";

            // Создание запроса. Переменная con — это объект типа Connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Установка параметров
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);

            // Выполнение запроса
            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }


        

    }
}
