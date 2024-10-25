package entity.listener;

import entity.Car;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDate;


public class CarEventListener {
    @PrePersist
    public void prePersist(Car entity){
        entity.setCreatedDATE(LocalDate.now());
    }


}
