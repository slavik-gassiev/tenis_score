package com.slava.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hello")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Передача параметра на JSP-страницу
        request.setAttribute("message", "Hello from Servlet!");

        // Перенаправление на JSP
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}