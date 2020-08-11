package ru.job4j.todo.persistance;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DBItemFindAllTest {
    private final DBItem dbItem = DBItem.instOf();

    @Before
    public void clearDb() {
        dbItem.deleteAllUsers();
        dbItem.deleteAllItems();
    }

    @Test
    public void whenFindAllItems() {
        User user = new User("user", "u4.com", "up");
        User u = dbItem.addUser(user);
        Item first = new Item("first");
        first.setUser(u);
        Item second = new Item("second");
        second.setUser(u);
        Item resultF = dbItem.addItem(first, u);
        Item resultS = dbItem.addItem(second, u);
        Item expectedFirst = new Item("first");
        expectedFirst.setUser(u);
        expectedFirst.setId(resultF.getId());
        Item expectedSecond = new Item("second");
        expectedSecond.setUser(u);
        expectedSecond.setId(resultS.getId());
        List<Item> expected = List.of(expectedFirst, expectedSecond);
        assertThat(dbItem.findAll(), is(expected));
    }
}
