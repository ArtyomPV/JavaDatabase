package dao;

import entity.Worker;

import java.util.List;

public interface WorkerDAO {
    public void addWorker(Worker worker);
    public void updateWorker(Worker worker);
    public List<Worker> getAllWorkers();
    public Worker getWorkerById(Integer id);
    public void removeWorker(Worker worker);
}
