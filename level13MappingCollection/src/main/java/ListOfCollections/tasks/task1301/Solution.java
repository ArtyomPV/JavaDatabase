package ListOfCollections.tasks.task1301;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {

        setAuthorToTable();



//        try (Session session = MySessionFactory.getSessionFactory().openSession()) {
//            Query<Author> query = session.createQuery("from Author", Author.class);
//            List<Author> authors = query.list();
//
//            authors.forEach(author -> {
//                Set<String> articles = author.getArticles();
//                if(articles.size()> 0){
//                    System.out.println(author.getFullName() + ": ");
//                    articles.forEach(System.out::println);
//                }
//            });
//
//        }

        MySessionFactory.getSessionFactory().inTransaction(session -> {
            List<String> achievement = new ArrayList<>(List.of("Kindness", "Brave"));
            Author author = session.find(Author.class, 1);
            author.setAchievements(achievement);
            session.merge(author);
        });

        try (Session session = MySessionFactory.getSessionFactory().openSession()) {
        Query<Author> query = session.createQuery("from Author", Author.class);
            List<Author> authors = query.list();

            authors.forEach(author -> {
                System.out.println(author.getFullName() + ": ");
                author.getAchievements().forEach(System.out::println);
            });

        }
    }

    private static void setAuthorToTable() {
        Set<String> miniArticles = Set.of("Hibernate", "session");
        Author author1 = new Author();
        author1.setFirstName("Arkasha");
        author1.setLastName("Prutikov");
        author1.setFullName("Arkasha V. Prutikov");
        author1.setArticles(miniArticles);

        MySessionFactory.getSessionFactory().inTransaction(session -> {
            session.persist(author1);
        });

    }
}
