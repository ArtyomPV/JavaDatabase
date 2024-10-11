package ru.prusov.lecture02readResultSet;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;



public class Runner {
    public static ResultSet resultSet;
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javarush",
                "root", "root");
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("Select * from test");

//        showStartPointResultSet();
//        getRowContentDB();
        getDataFromResultSet();
        statement.close();
        connection.close();

    }



    private static void getDataFromResultSet() throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            String name = resultSetMetaData.getColumnName(column);
            String columnClassName = resultSetMetaData.getColumnClassName(column);
            String columnTypeName = resultSetMetaData.getColumnTypeName(column);
            int columnType = resultSetMetaData.getColumnType(column);

            System.out.println(name + "\t" + columnClassName + "\t" + columnTypeName + "\t" + columnType);
        }
//        String columnName = resultSetMetaData.getColumnName(1);
//
//        System.out.println(columnCount);
//        System.out.println(columnName);
    }

    private static void getRowContentDB() throws SQLException {
        while(resultSet.next()){
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println(resultSet.getRow() + "." + id + "\t" + name);
        }
    }

    private static void showStartPointResultSet() throws SQLException {
        System.out.println(resultSet.getRow());
        System.out.println(resultSet.isBeforeFirst());
        System.out.println(resultSet.isFirst());

        resultSet.next();

        System.out.println(resultSet.getRow());
        System.out.println(resultSet.isBeforeFirst());
        System.out.println(resultSet.isFirst());

        resultSet.next();

        System.out.println(resultSet.getRow());
        System.out.println(resultSet.isBeforeFirst());
        System.out.println(resultSet.isFirst());
    }
}
