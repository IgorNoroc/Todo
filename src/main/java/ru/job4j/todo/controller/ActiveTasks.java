package ru.job4j.todo.controller;

import com.google.gson.Gson;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@WebServlet("/activeTasks")
public class ActiveTasks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Item> items = DBItem.instOf().selectDone(false);
        String json = new Gson().toJson(items);
        PrintWriter out = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        out.println(json);
    }
}
