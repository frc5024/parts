package io.github.frc5024.parts.routes;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.retrylife.simplelogger.SimpleLogger;
import ca.retrylife.webtools.Redirect;
import io.github.frc5024.parts.auth.AuthenticationService;
import io.github.frc5024.parts.db.DB;

@WebServlet("/setup")
public class SetupRoute extends HttpServlet {
    private static final long serialVersionUID = -3935675379391987785L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Serve the setup page
        req.getRequestDispatcher("setup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get account info
        String user = req.getParameter("username");
        String password = req.getParameter("password");

        // Create DB
        try {
            DB.getInstance().initDB();
            SimpleLogger.log("Setup", "Created a new DB");
        } catch (SQLException e) {
            SimpleLogger.log("Setup", "Failed to create a new DB");
            resp.getOutputStream().print("Failed to init DB. Try again");
            e.printStackTrace();
            return;
        }

        // Add account
        try {
            DB.getInstance().addUser(user, AuthenticationService.getInstance().auth.hash(password), 1);
            SimpleLogger.log("Setup", String.format("Created a new admin user (%s)", user));
        } catch (SQLException e) {
            SimpleLogger.log("Setup", "Failed to create user");
        }

        // Go to the home page
        Redirect.redirTo(resp, "/parts");
    }

}