package ru.job4j.todo.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.function.Function;

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

    /**
     * Add item to database.
     *
     * @param item item.
     * @return item.
     */
    public Item addItem(Item item, User user) {
        item.setUser(user);
        request(session -> session.save(item));
        return item;
    }

    public User addUser(User user) {
        request(session -> session.save(user));
        return user;
    }

    /**
     * Find item by id in database.
     *
     * @param id item id.
     * @return item.
     */
    public Item findById(int id) {
        return request(
                session -> session.get(Item.class, id));
    }

    public User findByEmail(String email) {
        return (User) request(
                session -> session.createQuery("from User where email='" + email + "'").getSingleResult()
        );
    }

    /**
     * Update item in database.
     *
     * @param id   id.
     * @param item item.
     */
    public void update(int id, Item item) {
        request(session -> {
            session.update(String.valueOf(id), item);
            return null;
        });
    }

    /**
     * Selecting from database all items.
     *
     * @return list of items.
     */
    public Collection<Item> findAll() {
        return request(
                session -> session.createQuery("from Item").list());
    }

    /**
     * Selecting from database the items that have status done true or false.
     *
     * @param condition true or false.
     * @return list of items.
     */
    public Collection<Item> selectDone(boolean condition) {
        return request(
                session -> session.createQuery("from Item where done=" + condition).list()
        );
    }

    /**
     * Making function for request to database to exclude duplicating code.
     *
     * @param command request
     * @param <T>     model.
     * @return result of request.
     */
    private <T> T request(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
