package dao;

import entity.Worker;
import entity.WorkerTask;
import org.hibernate.SessionFactory;

import java.util.List;

public class WorkTaskDaoImpl implements WorkerTaskDAO{

    private SessionFactory sessionFactory;

    public WorkTaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addTask(WorkerTask task) {

    }

    @Override
    public void updateTask(WorkerTask task) {

    }

    @Override
    public List<Worker> getAllTasks() {
        return null;
    }

    @Override
    public Worker getTaskById(Integer id) {
        return null;
    }

    @Override
    public void remove(WorkerTask workerTask) {

    }


}
