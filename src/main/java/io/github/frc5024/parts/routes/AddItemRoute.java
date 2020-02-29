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
import io.github.frc5024.parts.db.DB.ItemInfo;

@WebServlet("/additem")
public class AddItemRoute extends HttpServlet {
    private static final long serialVersionUID = -113312850059788524L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Serve the new item page
        req.getRequestDispatcher("additem.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Read data
        String name = req.getParameter("name");
        String cost_str = req.getParameter("cost");
        int cost = Integer.parseInt((cost_str.equals("")) ? "0" : cost_str);
        String quantity_str = req.getParameter("quantity");
        int quantity = Integer.parseInt((quantity_str.equals("")) ? "1" : quantity_str);
        String home = req.getParameter("home");
        String description = req.getParameter("description");

        // Get a pre-existing location if this item is being updated
        String location = home;
        try {
            for (ItemInfo i : DB.getInstance().getAllItemInfo()) {

                // Check if i is this item
                if (i.name.equals(name)) {

                    // Make a call to delete this item so it can be replaced
                    DB.getInstance().rmItem(i.name);

                    // Get the item location
                    location = i.locations;
                    
                }
            }
        } catch (SQLException e) {
            SimpleLogger.log("AddItemRoute", "Could not search for item");
        }

        // Sanitize description
        description = description.replaceAll("\n", "<br>");

        SimpleLogger.log("AddItemRoute", String.format(
                "Adding %dx %s to the database with a cost of $%d, and a home of: %s", quantity, name, cost, home));

        // Add item to DB
        try {
            DB.getInstance().addItem(name, cost, quantity, home, location, description);
        } catch (SQLException e) {
            SimpleLogger.log("AddItemRoute", "Failed to add item due to SQLException");
        }

        // Send user back to items page
        Redirect.redirTo(resp, "/parts/itemadmin");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Read data
        String name = req.getParameter("name");

        SimpleLogger.log("AddItemRoute", "Request made to delete item: " + name);

        // Try to delete the item
        try {
            DB.getInstance().rmItem(name);
        } catch (SQLException e) {
            SimpleLogger.log("AddItemRoute", "Failed to delete item: " + name);
        }
    }

}