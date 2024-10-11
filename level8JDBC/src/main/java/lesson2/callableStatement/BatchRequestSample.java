package lesson2.callableStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class BatchRequestSample {
    public static void run(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush",
                    "root", "root");

            PreparedStatement statement = connection.prepareStatement("insert into contact (first_name, last_name, phone, email) values (?, ?, ?, ?)");
            long phoneNumber = 790000000;
            for (int i = 0; i < 10; i++) {
                phoneNumber += 1;
                // Заполняем параметры запроса
                statement.setString(1, "FirstName_" + i);
                statement.setString(2, "LastName_" + i);
                statement.setString(3, "+" + phoneNumber);
                statement.setString(4, "email" + i + "@test.com");


                //Запрос не выполняется, а складывается в буфер
                // который потом выполнится сразу для всех команд
                statement.addBatch();
            };

            // Выполняем все запросы сразу
            int[] results = statement.executeBatch();
            Arrays.stream(results).forEach(System.out::print);
            System.out.println(results);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
