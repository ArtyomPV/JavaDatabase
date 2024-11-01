package many_to_many.task1306.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "publisher", schema = "hibernate13_OneToMany")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @OneToMany
    @JoinColumn(name = "book_id")
    private Set<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

