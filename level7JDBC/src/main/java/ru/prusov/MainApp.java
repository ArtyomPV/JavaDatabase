package ru.prusov;

import java.sql.*;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        // создаем подключение к базе данных
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javarush",
                "root", "root");
        // Создаем объект statement
        Statement statement = connection.createStatement();
        // Выполняем запрос к базе данных
        // resultSet в нем хранится результат запроса
        ResultSet resultSet = statement.executeQuery("Select * from test");

        while (resultSet.next()){
            Integer id = resultSet.getInt(1);
            String name = resultSet.getString(2);

            System.out.println(resultSet.getRow() + ": " + id + "\t " + name);
        }
connection.close();

    }
}
