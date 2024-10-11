package lesson5.rowset;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Random;

public class RunnerApp {
    public static void main(String[] args) throws SQLException {
        createRowSet();
        changeLineWithRowSet();
    }

    /**
     * изменение строк через RowSet:
     */
    private static void changeLineWithRowSet() throws SQLException {
        // Подключаемся к базе данных
        RowSetFactory rsf = RowSetProvider.newFactory();
        CachedRowSet crs = rsf.createCachedRowSet();
        crs.setUrl("jdbc:mysql://localhost:3306/javarush");
        crs.setUsername("root");
        crs.setPassword("root");
        crs.setCommand("Select * from cars");
        crs.execute();

        // Этот тип операции может изменить только автономный RowSet
// Сначала перемещаем указатель на пустую (новую) строку, текущая позиция запоминается
        crs.moveToInsertRow();
        Random Random = new Random();
        crs.setInt(1, 7);
        crs.setString(2, "Mazda");
        crs.setInt(3, 2009);
        crs.setInt(3, 1);
        crs.insertRow(); // Добавляем текущую (новую) строку к остальные строкам
        crs.moveToCurrentRow(); // Возвращаем указатель на ту строку, где он был до вставки

        crs.beforeFirst();
        while(crs.next()){
            System.out.println(crs.getString(1) + "\t" + crs.getString(2) + "\t" + crs.getString(3) );
        }
        // А теперь мы можем все наши изменения залить в базу
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javarush", "root", "root");
        con.setAutoCommit(false); // Нужно для синхронизации
        crs.acceptChanges(con);// Синхронизировать данные в базу данных
    }

    /**
     * Кэширует данные ResultSet с помощью  CachedRowSet
     * @throws SQLException
     */
    private static void createRowSet() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javarush",
                "root", "root");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from cars");

        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet crs = factory.createCachedRowSet();
        crs.populate(resultSet);

        connection.close();
        while(crs.next()){
            System.out.println(crs.getString(1) + "\t" + crs.getString(2) + "\t" + crs.getString(3) );
        }
    }


}
