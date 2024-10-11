package ru.prusov.lecture02readResultSet.postgresql;

import java.sql.*;

public class RunnerApp {
    public static ResultSet resultSet;
//    private static String SQL_URL = "jdbc:postgresql://localhost/exampleh";
    private static String SQL_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String SQL_USER = "postgres";
    private static String SQL_PASSWORD = "root";
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASSWORD)) {
            Statement statement = connection.createStatement();
           resultSet = statement.executeQuery("select * from exampleh.human");
            while(resultSet.next()){
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getInt(3));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
