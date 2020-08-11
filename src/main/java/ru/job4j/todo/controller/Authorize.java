package ru.job4j.todo.controller;


import ru.job4j.todo.model.User;
import ru.job4j.todo.persistance.DBItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/auth")
public class Authorize extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = null;
        try {
            user = DBItem.instOf().findByEmail(email);
        } catch (SQLException e) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/tasks.html");
        }
    }
}
