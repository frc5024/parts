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
import io.github.frc5024.parts.db.DB.ItemInfo;

@WebServlet("/itemadmin")
public class ItemAdminRoute extends HttpServlet {
    private static final long serialVersionUID = 7072217990277365949L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Only display if perms are obtained
        if (!AuthenticationService.getInstance().hasAdmin()) {
            Redirect.redirTo(resp, String.format("/parts?loggedIn=%s&admin=%s", false, false));
            return;
        }

        // Get a list of all items in the DB
        ArrayList<ItemInfo> items = new ArrayList<>();

        // Load items
        try {
            items = DB.getInstance().getAllItemInfo();
        } catch (SQLException e) {
            SimpleLogger.log("ItemAdminRoute", "Could not read items");
        }

        // Build a row for each item
        StringBuilder sb = new StringBuilder();

        // Build a row for each item
        for (ItemInfo i : items) {

            sb.append(String.format("<tr><td><input type='text' class='form-control' value='%s' id='name' name='name'></td>"
                    + "<td><input type='text' class='form-control' value='%d' id='cost' name='cost'></td>"
                    + "<td><input type='text' class='form-control' value='%d' id='quantity' name='quantity'></td>"
                    + "<td><input type='text' class='form-control' value='%s' id='home' name='home'></td>"
                    + "<td>%s</td>"
                    + "<td><textarea type='text' class='form-control' id='description' name='description' rows='3'>%s</textarea></td>"
                    + "<td><button type='submit' class='btn btn-primary' onclick='editItem(\"%s\");'>Update</button><br><button  class='btn btn-danger' onclick='delItem(\"%s\");'>Delete</button></td></tr>", i.name, i.cost, i.quantity, i.home, i.locations, i.info, i.name, i.name));
        }

        // Push list of items
        req.setAttribute("items", sb.toString());

        // Display the items admin page
        req.getRequestDispatcher("itemadmin.jsp").forward(req, resp);

    }

}