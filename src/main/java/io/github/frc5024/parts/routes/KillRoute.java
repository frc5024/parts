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

@WebServlet("/admin/kill")
public class KillRoute extends HttpServlet {
    private static final long serialVersionUID = 7430748897662544187L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Kill the app
        SimpleLogger.log("KillRoute", "User has requested shutdown");
        AuthenticationService.getInstance().logout();
        try {
            DB.getInstance().close();
        } catch (SQLException e) {
            SimpleLogger.log("KillRoute", "Failed to kill DB!");
        }
        System.exit(0);
    }

}