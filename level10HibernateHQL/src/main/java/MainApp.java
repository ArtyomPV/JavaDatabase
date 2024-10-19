import config.SessionFactoryConfig;
import dao.WorkTaskDaoImpl;
import dao.WorkerDAO;
import dao.WorkerDaoImpl;
import dao.WorkerTaskDAO;
import entity.Worker;
import entity.WorkerTask;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryConfig.buildSessionFactory();
        WorkerDAO workerDAO = new WorkerDaoImpl(sessionFactory);
        WorkerTaskDAO workTaskDao = new WorkTaskDaoImpl(sessionFactory);
        WorkerDaoImpl workerDao = new WorkerDaoImpl(sessionFactory);

        Worker ivan = Worker.builder()
                .name("Ivan")
                .salary(2000)
                .joinDate(LocalDate.now())
                .build();

        Worker valera = Worker.builder()
                .name("Valera")
                .salary(1500)
                .joinDate(LocalDate.now())
                .build();

        workerDAO.addWorker(ivan);
        workerDAO.addWorker(valera);
        List<Worker> allWorkers = workerDAO.getAllWorkers();

        for (Worker worker : allWorkers) {
            System.out.println(worker);
        }

        List<WorkerTask> tasks = workerDao.getAllTaskByWorkerName("Valera");
        System.out.println(tasks);
//
//        Worker vasya = Worker.builder()
//                .name("Vasya")
//                .salary(1700)
//                .joinDate(LocalDate.now())
//                .build();
//
//        workerDAO.updateWorker(vasya);

//        for (Worker worker : workerDAO.getAllWorkers()) {
//            System.out.println(worker);
//        }


    }
}
