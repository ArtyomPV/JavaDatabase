import config.SessionFactoryConfig;
import dao.CarDAO;
import dao.CarDAOImpl;
import dao.HumanDAO;
import dao.HumanDAOImpl;
import dto.HumanDTO;
import entity.Car;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import services.HumanService;

import java.util.List;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactory();

        HumanDAO humanDAO = new HumanDAOImpl(sessionFactory);
        HumanService humanService = new HumanService(humanDAO);

        Human valera = Human.builder()
                .name("Valera")
                .age(35)
                .build();
//        humanService.addHuman(valera);
        Human vasya = Human.builder()
                .id(3)
                .name("Vasya")
                .age(42)
                .build();
//        humanService.updateHuman(vasya);


        Session session = sessionFactory.openSession();

        valera = session.find(Human.class, 1);
        System.out.println(valera);

//        Query<HumanDTO> query = session.createQuery(
//                "select h.age, h.name, c.carName from Human h left join fetch Car c on c.human.id=h.id", HumanDTO.class);
//        List<HumanDTO> resultList = query.getResultList();
//        System.out.println(resultList);


        Car volvo = Car.builder()
                .carName("Volvo")
                .human(vasya)
                .build();

        CarDAOImpl carDAO = new CarDAOImpl(sessionFactory);
        carDAO.addCar(volvo);


    }
}
