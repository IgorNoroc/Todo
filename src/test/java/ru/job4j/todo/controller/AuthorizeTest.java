package ru.job4j.todo.controller;

import org.junit.Before;
import org.junit.Test;
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

public class AuthorizeTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final DBItem dbItem = DBItem.instOf();

    @Before
    public void clearDb() {
        dbItem.deleteAllUsers();
        dbItem.deleteAllItems();
    }

    @Test
    public void whenAuthorize() throws ServletException, IOException {
        Authorize servlet = new Authorize();
        User user = new User("user", "u.com", "up");
        dbItem.addUser(user);
        when(request.getParameter("email")).thenReturn("u.com");
        when(request.getParameter("password")).thenReturn("up");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        servlet.doPost(request, response);
        User result = (User) request.getSession().getAttribute("user");
        assertThat(result, is(user));
    }
}