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
        int j = 0;
        for (ItemInfo i : items) {

            sb.append(String.format(
                    "<tr><form method='POST' action='/parts/additem' id='item-%d'></form><td><input  type='text' class='form-control' form='item-%d' value='%s' id='name' name='name'></td>"
                            + "<td><input type='text' class='form-control' form='item-%d' value='%d' id='cost' name='cost'></td>"
                            + "<td><input type='text' class='form-control' form='item-%d' value='%d' id='quantity' name='quantity'></td>"
                            + "<td><input type='text' class='form-control' form='item-%d' value='%s' id='home' name='home'></td>"
                            + "<td>%s</td>"
                            + "<td><textarea type='text' class='form-control' id='description' form='item-%d'  name='description' rows='3'>%s</textarea></td>"
                            + "<td><button type='submit' form='item-%d' class='btn btn-primary'>Update</button></td></form>"
                            + "<td><button  class='btn btn-danger' onclick='delItem(\"%s\");'>Delete</button></td></tr>",
                            j,j, i.name,j, i.cost,j, i.quantity,j, i.home, i.locations,j, i.info, j,i.name));

            j++;
        }

        // Push list of items
        req.setAttribute("items", sb.toString());

        // Display the items admin page
        req.getRequestDispatcher("itemadmin.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}