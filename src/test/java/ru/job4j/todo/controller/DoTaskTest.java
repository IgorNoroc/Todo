package ru.job4j.todo.controller;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DoTaskTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final DBItem dbItem = DBItem.instOf();

    @Before
    public void clearDb() {
        dbItem.deleteAllUsers();
        dbItem.deleteAllItems();
    }

    @Test
    public void whenDoTask() {
        DoTask servlet = new DoTask();
        User user = new User("user", "u.com", "up");
        dbItem.addUser(user);
        Item item = new Item("test");
        int id = dbItem.addItem(item, user).getId();
        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        servlet.doPost(request, response);
        assertThat(dbItem.findById(id).isDone(), is(true));
    }
}