package entity.lecture1.task1201;


import jakarta.persistence.*;

@Entity
@Table(name="library_table")
public class Library {

    //напишите тут ваш код
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name="status")
    private PublicationStatus status;
    @Column(name="publication_name")
    private String publicationName;
    @Column(name="isbn")
    private String isbn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublicationStatus getStatus() {
        return status;
    }

    public void setStatus(PublicationStatus status) {
        this.status = status;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
