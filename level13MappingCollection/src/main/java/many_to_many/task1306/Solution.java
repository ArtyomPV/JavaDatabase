package many_to_many.task1306;


import many_to_many.task1306.config.MySessionFactory;
import many_to_many.task1306.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
/*
В методе main получи сессию из MySessionFactory.getSessionFactory().
Напиши hql-запрос для получения книг с полным именем 'Mark Twain'.

Выведи в консоль все его книги.
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        try (Session session = MySessionFactory.getSessionFactory().openSession()) {
            Query<Author> query = session.createQuery("from Author where fullName = :AUTHOR_FULLNAME", Author.class);
            query.setParameter("AUTHOR_FULLNAME", "Petr V. Dubin");
            Author authorMarkTwain = query.getSingleResult();

            authorMarkTwain.getBooks()
                    .stream()
                    .flatMap(book -> book.getAuthors().stream())
                    .filter(author -> authorMarkTwain.equals(author))
                    .distinct()
                    .map(Author::getFullName)
                    .forEach(System.out::println);
        }
    }
}
