package onetomany;

import config.SessionFactoryConfig;
import onetomany.entity.Contact;
import onetomany.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();

//        fillDB(sessionFactory);
            List<User> users = getAllUsers(sessionFactory);

       // Найти пользователя у которого тип сообщения message
        sessionFactory.inTransaction(session -> {
            String hql = "select u from User u join u.contacts c where c.type = :type";
//            String hql = "from User";
            List<User> usersQuery = session.createQuery(hql, User.class)
                    .setParameter("type", "email")
                            .getResultList();
            System.out.println(usersQuery);
            System.out.println("-----------------");
            List<Contact> message = usersQuery.stream()
//                    .map(user -> user.getContacts())
                    .flatMap(user -> user.getContacts().stream())
//                    .map(contact -> contact.getType())
                    .filter(contact -> contact.getType().equals("message")).toList();
            System.out.println(message);

        });
    }

    private static List<User> getAllUsers(SessionFactory sessionFactory) {
        List<User> users;
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("from User", User.class).list();
        } catch (Exception e){
            e.printStackTrace();
            users=new ArrayList<>();
        }
return users;
    }

    private static void fillDB(SessionFactory sessionFactory) {
        Contact phoneContact = Contact.builder().type("phone").data("Simple").build();
        Contact messageContact = Contact.builder().type("message").data("meeting").build();
        List<Contact> contacts = List.of(phoneContact,messageContact);
        User duke = User.builder()
                .name("Duke")
                .contacts(contacts)
                .build();

        sessionFactory.inTransaction(session -> {
            session.persist(phoneContact);
            session.persist(messageContact);
            session.persist(duke);
        });
    }
}
