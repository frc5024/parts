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

@WebServlet("/adduser")
public class AddUserRoute extends HttpServlet {
    private static final long serialVersionUID = 4727094826140079600L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Serve the user addition page
        req.getRequestDispatcher("adduser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Get account info
        String user = req.getParameter("username");
        String password = req.getParameter("password");
        boolean permission = req.getParameter("admin") != null;

        // Add account
        try {
            DB.getInstance().addUser(user, AuthenticationService.getInstance().auth.hash(password),
                    (permission) ? 1 : 0);
            SimpleLogger.log("AddUserRoute",
                    String.format("Created a new user (%s) using permission: %d", user, (permission) ? 1 : 0));
        } catch (SQLException e) {
            SimpleLogger.log("AddUserRoute", "Failed to create user");
        }

        // Go to the User admin page
        Redirect.redirTo(resp, "/parts/useradmin");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Read user request
        String user = req.getParameter("username");

        SimpleLogger.log("AddUserRoute", "Request made to delete user: " + user);

        // Try to delete the user
        try {
            DB.getInstance().rmUser(user);
        } catch (SQLException e) {
            SimpleLogger.log("AddUserRoute", "Failed to delete user: " + user);
        }
    }

}