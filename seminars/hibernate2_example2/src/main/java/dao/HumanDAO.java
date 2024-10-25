package dao;

import entity.Human;

import java.util.List;

public interface HumanDAO {
    public List<Human> getAllHumans();
    public void addHuman(Human human);
    public Human getHumanById(Integer id);

    public void deleteHumanById(Integer id);

    public void updateHumanById(Human human);
}
