package listener;

import entity.seminar4.Human;
import jakarta.persistence.PrePersist;

import java.time.LocalDate;


public class HumanEventListener {
    @PrePersist
    public void onUpdate(Human entity) {
        entity.setCreateDate(LocalDate.now());
    }
}
