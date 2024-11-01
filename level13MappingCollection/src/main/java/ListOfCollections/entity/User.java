package ListOfCollections.entity;

import jakarta.persistence.*;


import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users", schema = "hibernate13_mappingCollections")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @ElementCollection
    @CollectionTable(name = "user_message",  schema = "hibernate13_mappingCollections", joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "messages")
    public Set<String> messages;

    @ElementCollection
    @CollectionTable(name = "user_answer",
            schema = "hibernate13_mappingCollections",
            indexes = {@Index(columnList = "list_index")},
            joinColumns = @JoinColumn(name="user_id"))
    @OrderColumn(name = "list_index")
    @Column(name = "answers")
    public List<String> answers;

    @ElementCollection
    @CollectionTable(name="user_phone", schema = "hibernate13_mappingCollections", joinColumns = @JoinColumn(name="user_id"))
    @MapKeyColumn(name = "name")
    @Column(name = "phones")
    public Map<String, String> phones;


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

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Map<String, String> getPhones() {
        return phones;
    }

    public void setPhones(Map<String, String> phones) {
        this.phones = phones;
    }
}
