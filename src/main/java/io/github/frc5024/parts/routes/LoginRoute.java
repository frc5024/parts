package io.github.frc5024.parts.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.retrylife.webtools.Redirect;
import io.github.frc5024.parts.auth.AuthenticationService;

@WebServlet("/login")
public class LoginRoute extends HttpServlet {
    private static final long serialVersionUID = -2864208253450556793L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Determine if this is a logout request
        String logout = req.getParameter("logout");
        if (logout != null && logout.equals("1")) {
            // Log out of the backend
            AuthenticationService.getInstance().logout();

            // Redirect to home to force a client logout
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s",
                    AuthenticationService.getInstance().loggedIn(), AuthenticationService.getInstance().hasAdmin()));
        } else {

            // Serve the login view
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Read post data
        String user = req.getParameter("username");
        String password = req.getParameter("password");

        // Handle login
        boolean success = AuthenticationService.getInstance().login(user, password);

        if (success) {

            // Redirect to home
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s",
                    AuthenticationService.getInstance().loggedIn(), AuthenticationService.getInstance().hasAdmin()));

        } else {

            // Go back to login screen
            Redirect.redirTo(resp, "login?f=1");

        }
    }

}