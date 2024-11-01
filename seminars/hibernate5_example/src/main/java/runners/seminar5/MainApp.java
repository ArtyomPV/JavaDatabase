package runners.seminar5;

import config.SessionFactoryConfig;
import entity.seminar4.Car;
import entity.seminar4.Human;
import entity.seminar5.Book;
import entity.seminar5.Name;
import entity.seminar5.Student;
import entity.seminar5.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


public class MainApp {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactory("simenar5/hibernate.cfg.xml");

//        createEntityUser(sessionFactory);
//        getUserById(sessionFactory, 1);

//region Вставка студента со списком его книг
//        insertStudentWithHisBook(sessionFactory);

//endregion

//region получение студента Валера
//        getStudentValera(sessionFactory, 3);

//endregion
//region Получение всех студентов
//        getAllStudents(sessionFactory);
//endregion

//        insertStudentWithHisBookByCascade(sessionFactory);
//        insertBookWithStudentByCascade(sessionFactory);


        sessionFactory.inTransaction(session -> {
                    Student student = session.find(Student.class, 4);
                }
        );
    }

    /**
     * при наличии данной аннотации @ManyToOne(cascade = CascadeType.PERSIST)
     * вместе с книгой добавляется студент, который владеет книгой
     * @param sessionFactory
     */
    private static void insertBookWithStudentByCascade(SessionFactory sessionFactory) {
        Student boris = Student.builder()
                .name(Name.builder()
                        .firstName("Boris")
                        .middleName("K.")
                        .LastName("KisKis")
                        .build())
                .build();
        Book itTechology = Book.builder().bookName("IT techology").student(boris).build();
//        Book space = Book.builder().bookName("Space").student(boris).build();
//        List<Book> books = List.of(itTechology, space);
        sessionFactory.inTransaction(session -> session.persist(itTechology));
    }

    private static void getAllStudents(SessionFactory sessionFactory) {
        sessionFactory.inTransaction(session -> {
            Query<Student> query = session.createQuery("from Student", Student.class);
            System.out.println(query.getResultList());
        });
    }

    /**
     * при использовании (cascade = CascadeType.PERSIST) в БД записываются все связанные объекты одним запросом
     *
     * @param sessionFactory
     */
    private static void insertStudentWithHisBookByCascade(SessionFactory sessionFactory) {
        Name vasyaName = Name.builder().firstName("Vasia").middleName("D.").LastName("Dmitriev").build();
        Book physics = Book.builder().bookName("Physics").build();
        Book geometry = Book.builder().bookName("Geometry").build();
        Student vasya = Student.builder()
                .name(vasyaName)
                .books(List.of(physics, geometry))
                .build();

        sessionFactory.inTransaction(session -> session.persist(vasya));
    }

    private static void getStudentValera(SessionFactory sessionFactory, int id) {
        sessionFactory.inTransaction(session -> {
            Student student = session.get(Student.class, id);
            System.out.println(student);
        });
    }

    private static void insertStudentWithHisBook(SessionFactory sessionFactory) {
        List<Book> books = List.of(Book.builder().bookName("history").build());
        Student valera = Student.builder()
                .name(Name.builder()
                        .firstName("Valera")
                        .middleName("A.")
                        .LastName("Kavenkin")
                        .build())
                .books(books)
                .build();
        sessionFactory.inTransaction(session -> {
            session.persist(valera.getBooks().get(0));
            session.persist(valera);
        });
    }

    private static Book createEntityBook(SessionFactory sessionFactory, String bookName) {
        Book history = Book.builder().bookName(bookName).build();
        sessionFactory.inTransaction(session -> session.persist(history));
        return history;
    }


    private static Student createEntityStudent(SessionFactory sessionFactory) {
        List<Book> books = List.of(Book.builder().bookName("history").build());
        Student valera = Student.builder()
                .name(Name.builder()
                        .firstName("Valera")
                        .middleName("A.")
                        .LastName("Kavenkin")
                        .build())
                .books(books)
                .build();
        sessionFactory.inTransaction(session -> {
            session.persist(valera);
        });
        return valera;
    }

    private static void getUserById(SessionFactory sessionFactory, int id) {
        sessionFactory.inTransaction(session -> {
            User user = session.find(User.class, id);
            System.out.println(user);
        });
    }

    private static void createEntityUser(SessionFactory sessionFactory) {
        User valera = User.builder()
                .name(Name.builder()
                        .firstName("Valera")
                        .middleName("A.")
                        .LastName("Kavenkin")
                        .build())
                .build();
        sessionFactory.inTransaction(session -> {
            session.persist(valera);
        });
    }
}
