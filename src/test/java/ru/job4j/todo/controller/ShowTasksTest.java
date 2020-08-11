package ru.job4j.todo.controller;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowTasksTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final ServletOutputStream out = mock(ServletOutputStream.class);
    private final DBItem dbItem = DBItem.instOf();

    @Before
    public void clearDb() {
        dbItem.deleteAllUsers();
        dbItem.deleteAllItems();
    }

    @Test
    public void whenShowAllItems() throws IOException {
        ShowTasks servlet = new ShowTasks();
        User user = new User("user", "u.com", "up");
        dbItem.addUser(user);
        Item first = new Item("test1");
        Item second = new Item("test2");
        int idF = dbItem.addItem(first, user).getId();
        int idS = dbItem.addItem(second, user).getId();
        when(response.getOutputStream()).thenReturn(out);
        servlet.doGet(request, response);
        Item expectedFirst = new Item("test1");
        expectedFirst.setId(idF);
        expectedFirst.setUser(user);
        Item expectedSecond = new Item("test2");
        expectedSecond.setId(idS);
        expectedSecond.setUser(user);
        List<Item> expected = List.of(expectedFirst, expectedSecond);
        Collection<Item> result = dbItem.findAll();
        assertThat(expected, is(result));
    }
}