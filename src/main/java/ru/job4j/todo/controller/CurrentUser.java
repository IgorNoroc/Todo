package ru.job4j.todo.controller;

import com.google.gson.Gson;
import ru.job4j.todo.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/currentUser")
public class CurrentUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        String json = new Gson().toJson(user);
        PrintWriter out = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        out.println(json);
    }
}
