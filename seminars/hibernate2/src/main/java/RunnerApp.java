import config.SessionFactoryConfig;
import dto.HumanDto;
import dto.HumanRecordDto;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class RunnerApp {
    public static void main(String[] args) {
        SessionFactoryConfig sessionFactoryConfig = new SessionFactoryConfig();
        SessionFactory sessionFactory = sessionFactoryConfig
                .buildSessionFactoryConfiguration();

        getAllHumans(sessionFactory);
//        getAllHumansSortedByAge(sessionFactory);
//        getCountAllHumans(sessionFactory);
//        getAgeAndNameHumans(sessionFactory);
//        getAgeAndNameHumans1(sessionFactory);
//        updateAgeById(sessionFactory);
    }

    private static void updateAgeById(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        MutationQuery mutationQuery = session.createMutationQuery("update Human h set h.age=:newAge where h.id in (:id)");
        Transaction transaction = session.beginTransaction();
        mutationQuery.setParameterList("id", List.of(5,6));
        mutationQuery.setParameter("newAge", 37);
        mutationQuery.executeUpdate();
        transaction.commit();

    }

    private static void getAgeAndNameHumans1(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        List<HumanRecordDto> humanRecordDtoList = session.createQuery("select h.age, h.name from Human h", HumanRecordDto.class).getResultList();
        System.out.println(humanRecordDtoList);
    }

    private static void getAgeAndNameHumans(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        List<Object[]> ageAndNameHumans = session.createQuery("Select h.age, h.name from Human h", Object[].class).getResultList();

        List<HumanDto> humanDtos = ageAndNameHumans.stream()
                .map(objects -> new HumanDto((String) objects[1], (Integer) objects[0])).toList();

        System.out.println(humanDtos);


        }


    private static void getCountAllHumans(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Query<Long> query = session.createQuery("select count(*) from Human", Long.class);
        Long humanSize = query.getSingleResult();
        System.out.println(humanSize);
    }

    private static void getAllHumansSortedByAge(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Query<Human> humanQuery = session.createQuery("from Human h order by h.age asc", Human.class);
        List<Human> humans = humanQuery.getResultList();
        // можно выполнить сортировку на уровне backend
        List<Human> listHumans = humans.stream().sorted(Comparator.comparingInt(Human::getAge)).toList();
        System.out.println(humans);
    }

    private static void getAllHumans(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Query<Human> query = session.createQuery("from Human h", Human.class);
//        Query<Integer> query1 = session.createQuery("select h.id from Human h", Integer.class);
//        session.createMutationQuery();
        //Лучше получать всю сущность и уже работать с её полями
        List<Human> resultList = query.getResultList();
        System.out.println(resultList);

//        List<Integer> resultList1 = query1.getResultList();
//        System.out.println(resultList1);
    }





}
