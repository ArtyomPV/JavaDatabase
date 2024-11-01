package many_to_one.task1303;


import many_to_one.task1303.config.MySessionFactory;
import many_to_one.task1303.entity.Publisher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
/*
В методе main получи сессию из MySessionFactory.getSessionFactory().
Напиши hql-запрос для получения всех издателей,
которые публиковали автора 'Mark Twain'.

Выведи в консоль названия полученных издателей.
 */
public class Solution {
    public static void main(String[] args) throws Exception {
        List<Publisher> publishers;



        //напишите тут ваш код
        SessionFactory sessionFactory = MySessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "select b.publisher from Book b where b.author.fullName = :fullName";
        Query<Publisher> query = session.createQuery(hql, Publisher.class);
        query.setParameter("fullName", "Petr V. Dubin");
        publishers = query.list();
//        "select distinct b.publisher from Book b where b.author.fullName = :AUTHOR_FULLNAME
        publishers.stream().map(Publisher::getName).forEach(System.out::println);
    }
}
