import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class PaginateService {
    String URL = "jdbc:mysql://localhost:3306/javarush";
    String USERNAME = "root";
    String PASSWORD = "root";
    int poolSize = 10;
    private HikariDataSource dataSource;
    private HikariConfig config;

    //    Connection connection;
    public PaginateService() {
        config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(poolSize);

        dataSource = new HikariDataSource(config);
    }

    public boolean findByName(String name, int limit, int offset) {
        String sql = "Select * from cars limit ? offset ?";

        try (Connection connection = dataSource.getConnection()) {
            Statement statement1 = connection.createStatement();
            ResultSet executedQuery = statement1.executeQuery("select count(*) count from cars");
            executedQuery.next();
            int count = executedQuery.getInt("count");

            while (limit + offset <= count) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, limit);
                statement.setInt(2, offset);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    if(name.equals(resultSet.getString("name"))){
                        return true;
                    }
                }
                offset += limit;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
