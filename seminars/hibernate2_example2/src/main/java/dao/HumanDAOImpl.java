package dao;

import entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import java.util.List;

public class HumanDAOImpl implements HumanDAO{
    private SessionFactory sessionFactory;

    public HumanDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Human> getAllHumans() {
        String sql = "from Human h";
        Session session = sessionFactory.openSession();
        return session.createQuery(sql, Human.class).getResultList();
    }

    @Override
    public void addHuman(Human human) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(human);
        transaction.commit();
    }

    @Override
    public Human getHumanById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.get(Human.class, id);
    }

    @Override
    public void deleteHumanById(Integer id) {
        Session session = sessionFactory.openSession();
        Human human = session.get(Human.class, id);
        Transaction transaction = session.beginTransaction();
        session.remove(human);
        transaction.commit();
    }

    @Override
    public void updateHumanById(Human human) {
        String sql = "update Human set name=:name, age=:age where id=:id";
        Session session = sessionFactory.openSession();
        MutationQuery mutationQuery = session.createMutationQuery(sql);
        mutationQuery.setParameter("id", human.getId());
        mutationQuery.setParameter("name", human.getName());
        mutationQuery.setParameter("age", human.getAge());
        Transaction transaction = session.beginTransaction();
        mutationQuery.executeUpdate();
        transaction.commit();
    }
}
