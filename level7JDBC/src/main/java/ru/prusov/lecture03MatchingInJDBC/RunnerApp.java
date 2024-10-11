package ru.prusov.lecture03MatchingInJDBC;

import java.sql.*;

public class RunnerApp {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet results;

    public static void main(String[] args) throws SQLException {
        init();
        getResultsSet("Select * from test");

        closeAll();

    }

    private static void getResultsSet(String request) throws SQLException {
        results = statement.executeQuery(request);

        while(results.next()){
            int id = results.getInt(1);
            String name = results.getString(2);
            if(results.wasNull()){
                System.out.println(id + "\tName is a null");
            } else {
                System.out.println(id + "\tName is " + name);
            }
            int level = results.getInt(3);
            if(results.wasNull()){
                System.out.println(id + "\tlevel is a null");
            } else {
                System.out.println(id + "\tLevel is " + level);
            }
        }

    }

    private static void closeAll() throws SQLException {
        statement.close();
        connection.close();
    }

    private static void init() throws SQLException {
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush",
                "root", "root");
         statement = connection.createStatement();
    }
}
