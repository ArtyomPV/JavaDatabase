import config.SessionFactoryConfig;
import entity.Animal;
import entity.Human;
import lesson3.saveObjectToDB.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.Optional;

public class Main {
    private static SessionFactoryConfig sessionFactoryConfig;
    public static void main(String[] args) {
        sessionFactoryConfig = new SessionFactoryConfig();
        try(SessionFactory sessionFactory = sessionFactoryConfig.buildSessionFactoryConfiguration()){
            Session session = sessionFactory.openSession();
           Human valera =  Human.builder().name("Valera").age(30).build();
           Human vasya =  Human.builder().name("Vasya").age(30).build();
//           session.save(valera);
           // добавляет без транзакции
//           session.save(vasya);

            //region добавление human
           // с использованием транзакции
//            Transaction transaction = session.beginTransaction();
//            session.persist(valera);
//            transaction.commit();
            //endregion

            // region Получаем объект по id
            Human human = session.get(Human.class, 1);
            System.out.println(human);
            //endregion

            // region Delete human by id
            // Получаем объект из базы данных по id и потом только этот объект передаем на удаление

            // выполняем удаление через транзакции
//            Human human1 = session.get(Human.class, 2);
//            Transaction transaction1 =  session.beginTransaction();
//            session.remove(human1);
//            transaction1.commit();
            //endregion

            //region удаление Human с использованием Optional
//            Optional<Human> optionalHuman = session.byId(Human.class).loadOptional(3);
//            Transaction transaction2 = session.beginTransaction();
//            optionalHuman.ifPresent(session::remove);
//            transaction2.commit();
            //endregion

            // region получение Human
//            session.byId(Human.class).load(3);
//            Human petya = Human.builder().id(4).name("Petya").age(25).build();
//
//            Transaction transaction = session.beginTransaction();
//            session.merge(petya);
//            transaction.commit();

            // если ключа id нет то merge добавляет human
//            Human kolya = Human.builder().name("Kolya").age(12).build();
//            Transaction transaction1 = session.beginTransaction();
//            session.merge(kolya);
//            transaction1.commit();
            //endregion

            //region использование запроса, использование HQL
            Query<Human> query = session.createQuery("select h from Human h where id=5", Human.class);
            Human human1 = query.uniqueResult();
            System.out.println(human1);

            Transaction transaction = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery("update Human h set h.name='Slava' where h.id=:id");
            mutationQuery.setParameter("id", 8);
            mutationQuery.executeUpdate();
            transaction.commit();
            //endregion

            session.close();
            System.out.println("finished");
        }
    }


}
