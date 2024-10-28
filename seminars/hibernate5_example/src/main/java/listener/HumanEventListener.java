package listener;

import entity.Human;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDate;


public class HumanEventListener {
    @PrePersist
    public void onUpdate(Human entity) {
        entity.setCreateDate(LocalDate.now());
    }
}
