package entity.lecture3.task1203;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.ZonedDateTime;

//напишите тут ваш код
@Entity
@Table(name="time_table", schema = "hibernate4_mapping")
public class TimeClass {
    //напишите тут ваш код
    @Id
    private Long id;
    @Column(name="instant")
    private Instant instant;
    @Basic
    @Column(name="zoned_time ")
    private ZonedDateTime zonedDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }
}