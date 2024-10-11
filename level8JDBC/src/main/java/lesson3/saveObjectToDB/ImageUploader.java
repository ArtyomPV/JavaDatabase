package lesson3.saveObjectToDB;
/*
Создайте таблицу с полем типа BLOB:

CREATE TABLE images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image BLOB NOT NULL
);
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageUploader {
private final String DB_URL = "jdbc:mysql://localhost:3306/javarush";
private final String DB_USER = "root";
private final String DB_PASSWORD = "root";

//private final String imagePath;
//private final String imageName;

//public ImageUploader(String imagePath, String imageName){
//    this.imagePath = imagePath;
//    this.imageName = imageName;
//}

public void load(String imagePath, String imageName){
    try {
        uploadImage(imagePath, imageName);
    } catch (SQLException | IOException e){
        e.printStackTrace();
    }

}

private void uploadImage(String imagePath, String imageName) throws SQLException, IOException {
    Connection connection = DriverManager.getConnection(DB_URL, DB_USER ,DB_PASSWORD);
    String sql = "insert into images(name, image) values (?,?);";

    try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
        FileInputStream fis = new FileInputStream(new File(imagePath));)

    { preparedStatement.setString(1, imageName);
        preparedStatement.setBinaryStream(2, fis, (int) new File(imagePath).length());
        int rowAffect = preparedStatement.executeUpdate();

        if(rowAffect>0) System.out.println("Image loaded successfully");

    } finally {
        connection.close();
    }
}



}
