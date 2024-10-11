package lesson0;

import javax.swing.*;
import java.sql.*;

public class Runner {
    public static void main(String[] args) throws SQLException {

//        transactionCorrect();
//        transactionWithException();

        transactionWithCommit();
        transactionWithSavepoint();





    }

    private static void transactionWithSavepoint() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javarush",
                    "root", "root");
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            int rowCount1 = statement.executeUpdate("update workers set salary = salary + 100 where id = 1");
            int rowCount2 = statement.executeUpdate("update workers set salary = salary - 100 where id = 2");
            Savepoint savepoint = connection.setSavepoint();

            try{
            int rowCount3 = statement.executeUpdate("update workers set salary = salary + 100 where id = 3");
            } catch (SQLException e){
                connection.rollback(savepoint);
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if(connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void transactionWithCommit()  {
        Connection connection = null;
        try{
             connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javarush",
                    "root",
                    "root");
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            int rowCount1 = statement.executeUpdate("update workers set salary = salary + 100");
            int rowCount2 = statement.executeUpdate("update workers set salary = salary + 100");
            int rowCount3 = statement.executeUpdate("update workers get salary = salary + 100");
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
            connection.close();
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private static void transactionWithException() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javarush",
                "root", "root");

        Statement statement = connection.createStatement();
        int rowCount1 = statement.executeUpdate("update workers set salary = salary + 100");
        int rowCount2 = statement.executeUpdate("update workers set salary = salary + 100");
        int rowCount3 = statement.executeUpdate("update workers get something wrong");
        connection.close();
    }

    private static void transactionCorrect() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush",
                "root", "root")) {

            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            int rowCount1 = statement.executeUpdate("update workers set salary = salary + 200");
            int rowCount2 = statement.executeUpdate("update workers set salary = salary + 200");
            int rowCount3 = statement.executeUpdate("update workers set salary = salary + 200");
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
}
