import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyStatement;
import domain.Car;
import domain.Human;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Runner {

    public static void main(String[] args) {
        withdraw(1, 2, 100);

        PaginateService paginateService = new PaginateService();
        boolean isFound = paginateService.findByName("Mosckvich", 2, 0);
        System.out.println(isFound);
    }

    private static void withdraw(int from, int to, int amount) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/javarush");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.setMaximumPoolSize(10);

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        try {
            Connection connection = hikariDataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("Select e.id, e.name, c.id, c.name, c.year from employees e left join cars c on e.id = c.employee_id ");

            HashMap<Integer, Human> humans = new HashMap<>();
            while (results.next()) {
                Human human = new Human();
                int humanId = results.getInt(1);
                human.setId(humanId);
                human.setName(results.getString(2));
                if(!humans.containsKey(humanId)) humans.put(humanId, human);
                Car car = new Car();
                int carId = results.getInt(3);
                car.setId(carId);
                car.setName(results.getString(4));
                car.setYear(results.getInt(5));
                if(carId != 0) {
                    humans.get(humanId).getCars().add(car);
                }
            }
            System.out.println(humans);

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }
}
