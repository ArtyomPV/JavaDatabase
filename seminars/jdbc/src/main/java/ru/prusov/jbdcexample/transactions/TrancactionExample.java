package ru.prusov.jbdcexample.transactions;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.prusov.jbdcexample.domain.Human;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrancactionExample {
    private static int amount = 10;
    public static void main(String[] args) {
        withdraw(1, 2, amount);

    }
    static <connection> void withdraw(int from, int to, int amount) {
        List<Human> humans = new ArrayList<>();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/javarush");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.setMaximumPoolSize(10);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection connection = null;
        try {
            connection = hikariDataSource.getConnection();
            // connection transaction
            // запрещаем выполнение автоматического коммита
            connection.setAutoCommit(false);
            // закрываем ресурс, autocloseable
            try(PreparedStatement statement = connection.prepareStatement("SELECT * from humans where id = ? or id = ?");) {
                statement.setInt(1, from);
                statement.setInt(2, to);
            // закрываем ресурс, autocloseable
                try(ResultSet resultSet = statement.executeQuery();) {

                    while (resultSet.next()) {
                        humans.add(Human.builder().
                                id(resultSet.getInt("id")).
                                name(resultSet.getString("name")).
                                balance(resultSet.getInt("balance")).
                                build());
                    }
                }
            }

            Human fromHuman = humans.stream().filter(human -> human.getId().equals(from)).findFirst().get();
            Human toHuman = humans.stream().filter(human -> human.getId().equals(to)).findFirst().get();

            int balanceFrom = fromHuman.getBalance() - amount;
            int balanceTo = toHuman.getBalance() + amount;
            PreparedStatement preparedStatement1 = null;
            try {
                preparedStatement1 = connection.prepareStatement("update humans set balance = ? where id = ?");
                preparedStatement1.setInt(1, balanceFrom);
                preparedStatement1.setInt(2, fromHuman.getId());

                preparedStatement1.executeUpdate();
            } finally {
                preparedStatement1.close();
            }
            PreparedStatement preparedStatement2 = null;
            try {
                preparedStatement2 = connection.prepareStatement("update humans set balance = ? where id = ?");
                preparedStatement2.setInt(1, balanceTo);
                preparedStatement2.setInt(2, toHuman.getId());

                preparedStatement2.executeUpdate();
            } finally{
                preparedStatement2.close();
            }

            connection.commit();

        } catch (SQLException e ){
            try {
                // так как connection может быть null, делаем проверку
                if(connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                e.printStackTrace();
                System.err.println(ex.getErrorCode());
            }
        } finally {
            try {
                if(connection != null) {
                    // обязательно установить autocommit
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getErrorCode());
            }
        }
    }

}
