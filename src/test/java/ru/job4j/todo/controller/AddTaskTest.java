package ru.job4j.todo.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddTaskTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final DBItem dbItem = DBItem.instOf();

    @Before
    public void clearDb() {
        dbItem.deleteAllUsers();
        dbItem.deleteAllItems();
    }

    @Ignore
    @Test
    public void whenAddTask() throws ServletException, IOException {
        AddTask servlet = new AddTask();
        User user = new User("user", "u.com", "up");
        dbItem.addUser(user);
        when(request.getParameter("text")).thenReturn("test");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        servlet.doPost(request, response);
        Item expected = new Item("test");
        expected.setUser(user);
        expected.setId(1);
        Item result = dbItem.findById(1);
        assertThat(expected.getDescription(),
                is(result.getDescription()));
    }
}