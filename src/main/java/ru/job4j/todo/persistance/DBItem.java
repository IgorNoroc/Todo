package ru.job4j.todo.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;

public class DBItem {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    /**
     * Create singleton.
     */
    private static final class Lazy {
        private static final DBItem INST = new DBItem();
    }

    public static DBItem instOf() {
        return Lazy.INST;
    }

    public Item addItem(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public void update(int id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(String.valueOf(id), item);
        session.getTransaction().commit();
        session.close();
    }

    public Collection<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Collection<Item> selectDone(boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item where done=" + condition).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
