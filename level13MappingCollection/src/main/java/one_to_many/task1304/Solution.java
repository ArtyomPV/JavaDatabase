package one_to_many.task1304;


import one_to_many.task1304.config.MySessionFactory;
import one_to_many.task1304.entity.Author;
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
        List<Author> authors;



        //напишите тут ваш код
        SessionFactory sessionFactory = MySessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "from Author where fullName = :fullName";
        Query<Author> query = session.createQuery(hql, Author.class);
        query.setParameter("fullName", "Petr V. Dubin");
        authors = query.list();
//        "select distinct b.publisher from Book b where b.author.fullName = :AUTHOR_FULLNAME
        authors.stream().map(Author::getBooks).forEach(System.out::println);

    }
}
