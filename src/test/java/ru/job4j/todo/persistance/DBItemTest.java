package ru.job4j.todo.persistance;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DBItemTest {
    private final DBItem dbItem = DBItem.instOf();
    private final Item item = new Item("test");

    @Before
    public void clearDb() {
        dbItem.deleteAllUsers();
        dbItem.deleteAllItems();
    }

    @Test
    public void whenFindByEmailUser() throws SQLException {
        User user = new User("user", "u1.com", "up");
        dbItem.addUser(user);
        User expected = dbItem.findByEmail("u1.com");
        assertThat(expected, is(user));
    }

    @Test
    public void whenAddItem() {
        User user = new User("user", "u2.com", "up");
        dbItem.addUser(user);
        Item result = dbItem.addItem(item, user);
        assertThat(dbItem.findById(result.getId()), is(item));
    }

    @Test
    public void whenUpdateItem() {
        User user = new User("user", "u3.com", "up");
        dbItem.addUser(user);
        Item result = dbItem.addItem(item, user);
        result.setDescription("update");
        dbItem.update(result.getId(), result);
        assertThat(dbItem.findById(
                result.getId()).getDescription(),
                is("update"));
    }

    @Test
    public void whenSelectDoneTrueItems() {
        User user = new User("user", "u5.com", "up");
        dbItem.addUser(user);
        Item first = new Item("first");
        first.setUser(user);
        Item second = new Item("second");
        second.setUser(user);
        second.setDone(true);
        dbItem.addItem(first, user);
        Item resultS = dbItem.addItem(second, user);
        Item expectedItem = new Item("second");
        expectedItem.setUser(user);
        expectedItem.setDone(true);
        expectedItem.setId(resultS.getId());
        List<Item> expected = List.of(expectedItem);
        assertThat(dbItem.selectDone(true), is(expected));
    }
}