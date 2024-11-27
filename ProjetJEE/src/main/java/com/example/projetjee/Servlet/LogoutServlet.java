package com.example.projetjee.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalider la session
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }

        // Rediriger vers login.jsp
        response.sendRedirect("login.jsp");
    }
}
