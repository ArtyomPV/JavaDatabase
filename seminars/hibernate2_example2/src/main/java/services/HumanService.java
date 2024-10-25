package services;

import dao.HumanDAO;
import entity.Human;

import java.util.List;

public class HumanService {
    private HumanDAO humanDAO;

    public HumanService(HumanDAO humanDAO) {
        this.humanDAO = humanDAO;
    }

    public void addHuman(Human human){
        humanDAO.addHuman(human);
    }
    public List<Human> getAllHuman(){
        return humanDAO.getAllHumans();
    }
     public Human findHuman(Integer id){
        return humanDAO.getHumanById(id);
     }

     public void deleteHuman(Integer id){
        humanDAO.deleteHumanById(id);
     }
     public void updateHuman(Human human){
        humanDAO.updateHumanById(human);
     }
}
