package lesson2.connectToDatabase;

import lesson2.connectToDatabase.entity.Human;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeManager {
    private SessionFactory sessionFactory;

    public void init(){
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public List<Human> getAllEmployees(){
        try(Session session = sessionFactory.openSession()){
            Query<Human> query = session.createQuery("from Human", Human.class);
            return query.list();
        }
    }

    public void addEmployee(Human human){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.getTransaction();
            session.save(human);
            transaction.commit();
        }
    }
    public Human getHumanById
}
