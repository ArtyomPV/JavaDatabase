package ru.prusov.jbdcexample.transactions;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class RunnerConnectionPool {
    private static String number = "1";
    public static void main(String[] args) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/javarush");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.setMaximumPoolSize(10);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        try( Connection connection = hikariDataSource.getConnection()){
           Statement statement = connection.createStatement();

           ResultSet resultSet = statement.executeQuery("select * from cars");
           resultSet.next();
               System.out.println(resultSet.getInt("id"));
//               System.out.println(resultSet.getString("name"));
//               System.out.println(resultSet.getInt("year"));

       }
    }
}
