package io.github.frc5024.parts.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.retrylife.webtools.Redirect;

@WebServlet("/login")
public class LoginRoute extends HttpServlet {
    private static final long serialVersionUID = -2864208253450556793L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Determine if this is a logout request
        String logout = req.getParameter("logout");
        if (logout != null && logout.equals("1")) {
            // TODO: Handle backend logout here

            // Redirect to home to force a client logout
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
        } else {

            // Serve the login view
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO: Handle login logic here
        boolean success = true;
        boolean hasAdmin = false;

        if (success) {

            // Redirect to home
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", true, hasAdmin));

        } else {

            // Go back to login screen
            Redirect.redirTo(resp, "login?f=1");

        }
    }

}