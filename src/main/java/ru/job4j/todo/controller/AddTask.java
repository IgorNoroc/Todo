package ru.job4j.todo.controller;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@WebServlet("/addTask")
public class AddTask extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String text = req.getParameter("text");
        Item item = new Item(text);
        item.setCreate(new Date(System.currentTimeMillis()));
        DBItem.instOf().addItem(
                item,
                (User) req.getSession().getAttribute("user"));
    }
}
