package io.github.frc5024.parts.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/setup")
public class SetupRoute extends HttpServlet{
    private static final long serialVersionUID = -3935675379391987785L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Serve the setup page
        req.getRequestDispatcher("setup.jsp").forward(req, resp);
    }
    
}