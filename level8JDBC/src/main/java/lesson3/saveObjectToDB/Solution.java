package lesson3.saveObjectToDB;

import lesson3.saveObjectToDB.model.Employee;
import lesson3.saveObjectToDB.model.Worker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Set;

public class Solution {

    public static void main(String[] args) throws Exception {
        Set<Worker> workers = Set.of(
                new Worker("Vasya", 33, "lead"),
                new Worker("Pasha", 40, "dev"),
                new Worker("Sasha", 40, "lead"),
                new Worker("Dima", 40, "director"),
                new Worker("Sasha", 40, "dev"));
        String sql = "insert into employee (name, age, smth) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            //напишите тут ваш код
            for(Worker worker: workers){
                statement.setString(1, worker.getName());
                statement.setInt(2, worker.getAge());
                statement.setString(3, worker.getOccupation());
                statement.addBatch();
            }
            statement.executeBatch();

        }
    }
}