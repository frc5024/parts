package io.github.frc5024.parts.routes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.retrylife.webtools.Redirect;
import io.github.frc5024.parts.auth.AuthenticationService;

@WebServlet("/admin")
public class AdminRoute extends HttpServlet {
    private static final long serialVersionUID = -430832979125973705L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Only display admin panel if user hs admin perms
        if (AuthenticationService.getInstance().hasAdmin()) {
            req.getRequestDispatcher("admin.jsp").forward(req, resp);
        } else {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
        }

    }
}