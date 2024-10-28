package lectureRunners.lecture02;

import config.SessionFactoryConfig;
import dao.UserDAO;
import entity.User;
import org.hibernate.SessionFactory;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        UserDAO userDAO = new UserDAO(sessionFactory);

        User nick = User.builder().name("Nick").build();
//region persist and merge
//        userDAO.persistUser(nick);
//
//        nick.setName("Nicky");
//        User user = userDAO.mergeUser(nick);
//        System.out.printf("updated user with new name %S \n", user.getName());
//endregion

// region create new record with merge
        // если данного объекта нет в базе то выполняется сохранение (persist)
//        User ken = User.builder().name("Ken").build();
//        User savedKen = userDAO.mergeUser(ken);
//        System.out.println(savedKen);
//endregion



//region updateUser
        userDAO.persistUser(nick);
        nick.setName("Nick1");
        userDAO.updateUser(nick);
//endregion
    }

}
