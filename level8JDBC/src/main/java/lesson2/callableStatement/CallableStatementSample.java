package lesson2.callableStatement;

import java.sql.*;

public class CallableStatementSample {
    public static void run() throws SQLException {
        // ПОДКЛЮЧЕНИЕ К СЕРВЕРУ
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javarush",
                "root", "root");

        // Создание объекта CallableStatement. он выполняет предварительную обработку
        // вызова хранимой процедуры. Знаки вопроса
        // указывают, где должны быть подставлены входные параметры, а где выходные
        // Первые два параметра являются входными, а третий - выходным

        CallableStatement callableStatement = connection.prepareCall("Call mylibrary.add(?, ?, ?)");

        // Настройка входных параметров. Передаем в процедуру 123 и 234
        callableStatement.setInt(1, 123);
        callableStatement.setInt(2, 234);

        // Регистрация типа выходного параметра
        callableStatement.registerOutParameter(3, Types.INTEGER);

        // Запуск хранимой процедуры
        callableStatement.execute();

        // Получение значения выходного параметра
        int sum = callableStatement.getInt(3);

        // Закрытие CallableStatement и Connection
        callableStatement.close();
        connection.close();
    }
}
