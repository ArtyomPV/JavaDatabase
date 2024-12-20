package many_to_many.task1306.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "author", schema = "hibernate13_ManyToMany")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    //напишите тут ваш код
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_book", schema = "hibernate13_ManyToMany",
            joinColumns=  @JoinColumn(name="author_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="book_id", referencedColumnName="id") )
    private Set<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
      public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
