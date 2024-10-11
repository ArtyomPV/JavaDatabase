package ru.prusov.jbdcexample.simplerequest;

import ru.prusov.jbdcexample.domain.Car;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

public class Runner {
    private static Connection connection;
    private static Statement statement;
    private static List<Car> cars ;
    private static String query = "Select id, name, year, employee_id from cars";
    private static int idForDelete = 1;
    public static void main(String[] args) throws SQLException {


         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush",
                "root", "root");

        statement = connection.createStatement();
//        addCarToBD("insert into cars (name, year, employee_id)\n" +
//                "values ('Peugeot', 2009, 1);");

//        executeQuery();

        // использование PreparedStatement
//        getCarByEmployeeNameWithPrepared("Anna");

        // получение даты и время из базы данных
        getDateTime();


        // здесь можно использовать sql injection
//        getCarByEmployeeName("Valers;");

//        executeQuery();
//        deleteCarById(idForDelete);
//        executeQuery();
    }

    private static void getDateTime() throws SQLException {
        ResultSet results = statement.executeQuery("Select create_date from tasks where id = 2;");
        while (results.next()){
            ResultSetMetaData resultSetMetaData = results.getMetaData();
            System.out.println(resultSetMetaData.getColumnType(1));
            Timestamp timestamp = results.getTimestamp("create_date");
            System.out.println(timestamp);

            LocalDateTime dateTime = results.getObject(1, LocalDateTime.class);
            System.out.println(dateTime);
//            System.out.println(results.getObject("created_date").);
        }
    }

    private static void getCarByEmployeeNameWithPrepared(String name) throws SQLException {
        cars = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select id, name, year, employee_id from cars\n" +
                "where employee_id in (select id from employee where name = ?);");
        preparedStatement.setString(1, name);
        ResultSet results = preparedStatement.executeQuery();
        while (results.next()) {
            cars.add(Car.builder().id(results.getInt("id")).
                    name(results.getString("name")).
                    year(results.getInt("year")).
                    employee_id(results.getInt("employee_id")).
                    build());
        }
        System.out.println(cars);

    }

    /**
     * Находи список автомобилей по имени сотрудника
     * @param name вводим имя сотрудника, по которому ищем список автомобилей
     * @throws SQLException
     */
    private static void getCarByEmployeeName(String name) throws SQLException {
        cars = new ArrayList<>();
        String query = String.format("select id, name, year, employee_id from cars\n" +
                "where employee_id in (select id from employee where name = '%s');", name);
        ResultSet results = statement.executeQuery(query);
        while (results.next()) {
            cars.add(Car.builder().id(results.getInt("id")).
                    name(results.getString("name")).
                    year(results.getInt("year")).
                    employee_id(results.getInt("employee_id")).
                    build());
        }
        System.out.println(cars);
    }



    private static void deleteCarById(int idForDelete) throws SQLException {
        String query1 = String.format("Delete from cars where id = %d", idForDelete);
        statement.executeUpdate(query1);
    }

    /**
     * Добавляем автомобиль в базу данных
     * @param message запрос в котором передаем характеристики автомобиля
     * @throws SQLException
     */
    private static void addCarToBD(String message) throws SQLException {
        int i = statement.executeUpdate(message);
        System.out.println(i);
    }

    /**
     * Получаем список всех позиций из базы данных таблицы cars
     * @throws SQLException
     */
    private static void executeQuery() throws SQLException {
        ResultSet results = statement.executeQuery(query);
        cars = new ArrayList<>();
        while (results.next()) {
            cars.add(Car.builder().id(results.getInt("id")).
                    name(results.getString("name")).
                    year(results.getInt("year")).
                    employee_id(results.getInt("employee_id")).
                    build());
        }

        System.out.println(cars);
    }
}
