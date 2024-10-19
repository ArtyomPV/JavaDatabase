import config.SessionFactoryConfig;
import dao.CarDao;
import dao.HumanDao;
import entity.Car;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();

        task1(sessionFactory);
    }

    private static void task1(SessionFactory sessionFactory) {
        HumanDao humanDao = new HumanDao(sessionFactory);
        CarDao carDao = new CarDao(sessionFactory);

        Human viktor = Human.builder()
                .name("Viktor")
                .age(25)
                .build();

        Car volvo = Car.builder().carName("Volvo").human(viktor).build();
        humanDao.addHuman(viktor);
        carDao.addCar(volvo);

        for (Human human : humanDao.getAllHumans()) {
            System.out.println(human);
        }


    }


}
