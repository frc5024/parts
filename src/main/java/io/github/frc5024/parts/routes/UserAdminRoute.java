package io.github.frc5024.parts.routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.retrylife.simplelogger.SimpleLogger;
import ca.retrylife.webtools.Redirect;
import io.github.frc5024.parts.auth.AuthenticationService;
import io.github.frc5024.parts.db.DB;
import io.github.frc5024.parts.db.DB.UserInfo;

@WebServlet("/useradmin")
public class UserAdminRoute extends HttpServlet {
    private static final long serialVersionUID = 520574573861166554L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Get user info
        ArrayList<UserInfo> users = null;

        try {
            users = DB.getInstance().getAllUserInfo();
        } catch (SQLException e) {
            SimpleLogger.log("UserAdminRoute", "Could not read users");
        }

        StringBuilder sb = new StringBuilder();

        // Build list of table rows
        for (UserInfo u : users) {
            sb.append(String.format(
                    "<tr><td>%s</td><td>%s</td><td>%d</td><td><button class='btn btn-danger mb-2' onclick='delUser(\"%s\");'>X</button></td></tr>", u.username, u.hash, u.perm, u.username));
        }

        req.setAttribute("users", sb.toString());

        // Display panel
        req.getRequestDispatcher("useradmin.jsp").forward(req, resp);
    }

}