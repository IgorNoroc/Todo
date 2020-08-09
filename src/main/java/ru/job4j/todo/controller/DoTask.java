package ru.job4j.todo.controller;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/doTask")
public class DoTask extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Item item = DBItem.instOf().findById(id);
        item.setDone(true);
        DBItem.instOf().update(id, item);
    }
}
