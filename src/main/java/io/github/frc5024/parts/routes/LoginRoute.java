package io.github.frc5024.parts.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginRoute extends HttpServlet {
    private static final long serialVersionUID = -2864208253450556793L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Serve the login view
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
    
}