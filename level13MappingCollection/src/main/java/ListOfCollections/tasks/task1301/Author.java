package ListOfCollections.tasks.task1301;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "author",  schema = "hibernate13_mappingCollections")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name="full_name")
    private String fullName;
    @ElementCollection
    @CollectionTable(name = "articles",
            schema = "hibernate13_mappingCollections",
            joinColumns = @JoinColumn(name = "author_id"))
            @Column(name = "article")
    private Set<String> articles;

    //напишите тут ваш код
    /*
    schema = "hibernate13_mappingCollections",
     */
    @ElementCollection
    @CollectionTable(name = "author_achievement",
            schema = "hibernate13_mappingCollections",
            joinColumns = @JoinColumn(name = "author_id"))
    @OrderColumn(name="achievement_index")
    @Column(name = "achievement")
    private List<String> achievements;


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

    public Set<String> getArticles() {
        return articles;
    }

    public void setArticles(Set<String> articles) {
        this.articles = articles;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }
}
