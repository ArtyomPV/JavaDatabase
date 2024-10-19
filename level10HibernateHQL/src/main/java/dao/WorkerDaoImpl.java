package dao;

import entity.Worker;
import entity.WorkerTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class WorkerDaoImpl implements WorkerDAO {
    private SessionFactory sessionFactory;

    public WorkerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addWorker(Worker worker) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(worker);
            transaction.commit();
        }

    }

    @Override
    public void updateWorker(Worker worker) {
        String hql = "update Worker w set w.name=:name, w.salary=:salary, w.joinDate=:joinDate where w.id=:id";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Worker worker1 = session.get(Worker.class, 2);
            worker1.setName("Kolya");


//            MutationQuery mutationQuery = session.createMutationQuery(hql);
//            mutationQuery.setParameter("name", worker.getName())
//                    .setParameter("salary", worker.getSalary())
//                    .setParameter("joinDate", worker.getJoinDate())
//                    .setParameter("id", worker.getId());
//            mutationQuery.executeUpdate();
            transaction.commit();

        }

    }

    @Override
    public List<Worker> getAllWorkers() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from Worker w", Worker.class).getResultList();


    }


    @Override
    public Worker getWorkerById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Worker.class, id);
        }
    }

    @Override
    public void removeWorker(Worker worker) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(worker);
            transaction.commit();
        }
    }

    public List<WorkerTask> getAllTaskByWorkerName(String name) {
        String hql = "from WorkerTask wt where worker.name=:name";
        try (Session session = sessionFactory.openSession()) {
            Query<WorkerTask> query = session.createQuery(hql, WorkerTask.class);
            query.setParameter("name", name);
            return query.getResultList();
        }
    }

    public List<Worker> getWorkerWithNotDoneTasks() {
        String hql = "select distinct worker from WorkerTask where deadline<" + LocalDate.now();
        try (Session session = sessionFactory.openSession()) {
            //TODO
            return session.createQuery(hql, Worker.class).getResultList();
        }
    }
}
