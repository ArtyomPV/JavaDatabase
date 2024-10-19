package dao;

import entity.Worker;
import entity.WorkerTask;

import java.util.List;

public interface WorkerTaskDAO {
    public void addTask(WorkerTask task);
    public void updateTask(WorkerTask task);
    public List<Worker> getAllTasks();
    public Worker getTaskById(Integer id);
    public void remove(WorkerTask workerTask);
}
