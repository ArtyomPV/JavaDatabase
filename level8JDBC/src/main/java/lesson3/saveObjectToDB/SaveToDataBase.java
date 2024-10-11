package lesson3.saveObjectToDB;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class SaveToDataBase {

    Connection connection;

    public SaveToDataBase(){
            init();
    }

    private void init() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javarush",
                    "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeConnection(){

            try {
                if(connection != null) {connection.close();}
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    private static void run(){
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javarush",
                    "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from contact");
            resultSet.first();

            Blob blob = resultSet.getBlob("image");
            InputStream is = blob.getBinaryStream();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void  createBlob(String fileName) throws SQLException {
        String insertQuery = "INSERT INTO images(name, image) VALUES (?, ?)";
       PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        // Создаем объект Blob и получаем у него OutputStream для записи в него данных
        Blob blob1 = connection.createBlob();

        // Заполняем Blob данными  …
        OutputStream os =  blob1.setBinaryStream(1);

        // Передаем Вlob как параметр запроса
        preparedStatement.setString(1, fileName);
        preparedStatement.setBlob(2, blob1);
        preparedStatement.execute();

        closeConnection();

    }



}
