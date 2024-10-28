package lectureRunners.lecture01;

import config.SessionFactoryConfig;
import dao.UserDAO;
import entity.User;
import jakarta.persistence.EntityExistsException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.action.internal.EntityActionVetoException;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        UserDAO userDAO = new UserDAO(sessionFactory);
        User valera = User.builder().name("Valera").build();
        User kolya = User.builder().name("Kolya").build();
        /*
        При использовании метода save() сохранение одного и  того же объекта происходит новую запись, в то время как
        persist() кинет исключение
         */
        Integer valeraId = userDAO.saveUser(valera);
        Integer valeraId2 = userDAO.saveUser(valera);
        System.out.printf("User %S created with id: %d \n",valera.getName(),valeraId);
        System.out.printf("User %S created with id: %d \n",valera.getName(),valeraId2);

        try {
        Integer integerId1 = userDAO.persistUser(kolya);
        System.out.printf("User %S created with id: %d \n",kolya.getName(),integerId1);
    } catch (EntityExistsException exception) {
        System.out.println("запись не произведена " + exception);
    }
        try {
            Integer integerId2 = userDAO.persistUser(kolya);
            System.out.printf("User %S created with id: %d \n",kolya.getName(),integerId2);
        } catch (EntityExistsException exception) {
            System.out.println("запись не произведена " + exception);
        }

    }
}
