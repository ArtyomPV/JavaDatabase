package dao;

import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HumanDao {
    private SessionFactory sessionFactory;

    public HumanDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void addHuman(Human human){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(human);
            transaction.commit();
        }
    }

    public Human getHumanById(Integer id){
        try (Session session = sessionFactory.openSession()) {
            return session.get(Human.class, id);
        }
    }


    public List<Human> getAllHumans(){
        try (Session session = sessionFactory.openSession()) {
            Query<Human> humanQuery = session.createQuery("from Human h", Human.class);
            return humanQuery.list();
        }
    }
}
