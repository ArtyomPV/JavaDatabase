package service;

import entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeService {
    private SessionFactory sessionFactory;

    public EmployeeService(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<String> findDistinctSortedEmployeeNameOlderThan(int age){
        String sql = String.format("select distinct name from Employee e where e.age>%d order by e.smth ", age);
       try(Session session = sessionFactory.openSession() ){
           Query<String> names = session.createQuery(sql, String.class);
           return names.list();
       }
    }

    public void addEmployee(Employee employee){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        }
    }
    public List<Employee> getAllEmployees(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Employee", Employee.class).list();
        }
    }
    public void promoteAll(){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("update Employee e set smth=concat('senior ', smth)");
            query.executeUpdate();
            transaction.commit();
        }
    }

    public List<Employee> findEmployeesBySmth(String smth){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery("from Employee e where e.smth='" + smth + "'", Employee.class);
            return query.list();
        }
    }

    public Integer UpdateEmployeesByIdMutationQuery(String smth, int id){
        String hql = "update Employee set smth=:smth where id=:id";

        try(Session session= sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery(hql);
            mutationQuery.setParameter("id", id);
            mutationQuery.setParameter("smth", smth);
            return mutationQuery.executeUpdate();
        }
    }
}
