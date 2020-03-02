package io.github.frc5024.parts.routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.retrylife.simplelogger.SimpleLogger;
import io.github.frc5024.parts.db.DB;
import io.github.frc5024.parts.db.DB.ItemInfo;

@WebServlet("/app")
public class IndexRoute extends HttpServlet {
    private static final long serialVersionUID = 588464291161226042L;

    private static final String ITEM_CARD = "<div class='inv-item card border-secondary' ><div class='card-header'>%s</div><div class='card-body text-secondary'>"
            + "<div class='item-params'><p style='min-width:300px;'><span class='badge badge-primary'>Storage location</span> %s</p>"
            + "<p><span class='badge badge-primary'>Cost</span> $%d</p>"
            + "<p><span class='badge badge-primary'>Quantity</span> %d</p></div><p class='card-text'>%s</p>"
            + "<hr><h5 class='card-title'>Known Locations</h5><ul>%s</ul><div class='form-inline my-2 my-lg-0'>"
            + "<input class='form-control mr-sm-2' type='search' placeholder='Add Location' id='%s-location-add' aria-label='Search'>"
            + "<button class='btn btn-outline-success my-2 my-sm-0' onclick='addLocation(\"%s\");'>+</button></div></div></div>";

    private static final String ITEM_LOCATION = "<li><a onclick='delLocation(\"%s\", \"%s\");' href='#'><span class='badge badge-danger'>X </span></a> %s</li>";

    private void pushPage(List<ItemInfo> items, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Build a list of cards
        StringBuilder sb = new StringBuilder();

        // Handle no items
        if (items.size() == 0) {
            sb.append("<div class='inv-item card border-secondary'><div class='card-header'>No items found.</div></div>");
        }

        // Fill cards
        for (ItemInfo i : items) {

            // Build locations list
            StringBuilder locations = new StringBuilder();
            for (String l : i.locations.split(",")) {

                // Skip empty location strings
                if (l.equals("")) {
                    continue;
                }

                // Add the location bullet
                locations.append(String.format(ITEM_LOCATION, i.name, l, l));
            }

            // Build on to the StringBuilder
            sb.append(String.format(ITEM_CARD, i.name, i.home, i.cost, i.quantity, i.info, locations, i.name, i.name));
            sb.append("<br>");
        }

        // Send items to jspx
        req.setAttribute("items", sb.toString());

        req.getRequestDispatcher("app.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get top 10 items
        ArrayList<ItemInfo> allItems = new ArrayList<>();
        try {
            allItems = DB.getInstance().getAllItemInfo();
        } catch (SQLException e) {
            SimpleLogger.log("IndexRoute", "Failed to read items");
        }

        // Push page
        pushPage((allItems.size() > 10) ? allItems.subList(0, 10) : allItems, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get all items
        ArrayList<ItemInfo> allItems = new ArrayList<>();
        try {
            allItems = DB.getInstance().getAllItemInfo();
        } catch (SQLException e) {
            SimpleLogger.log("IndexRoute", "Failed to read items");
            e.printStackTrace();
        }

        // Get keyword
        String keyword = req.getParameter("search");

        SimpleLogger.log("IndexRoute", "User searched for: " + keyword);

        // Find all items containing search term
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (ItemInfo i : allItems) {

            // Check if the search term is in the item data
            boolean inName = i.name.toUpperCase().contains(keyword.toUpperCase());
            boolean inInfo = i.info.toUpperCase().contains(keyword.toUpperCase());
            boolean inLocations = i.locations.toUpperCase().contains(keyword.toUpperCase());
            if (inName || inInfo || inLocations) {

                // Add the item to the list
                items.add(i);
            }
        }

        // Push page
        pushPage(items, req, resp);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Read the Item name
        String name = req.getParameter("name");
        String newLocation = req.getParameter("location");

        // Write new
        try {
            for (ItemInfo i : DB.getInstance().getAllItemInfo()) {

                // Check if i is this item
                if (i.name.equals(name)) {

                    // Make a call to delete this item so it can be replaced
                    DB.getInstance().rmItem(i.name);

                    // Push new item
                    DB.getInstance().addItem(i.name, i.cost, i.quantity, i.home, i.locations + "," + newLocation,
                            i.info);
                    SimpleLogger.log("IndexRoute", "Updated locations for: " + i.name);

                }
            }
        } catch (SQLException e) {
            SimpleLogger.log("IndexRoute", "Could not search for item");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Read the Item name
        String name = req.getParameter("name");
        String rmLocation = req.getParameter("location");

        // Write new
        try {
            for (ItemInfo i : DB.getInstance().getAllItemInfo()) {

                // Check if i is this item
                if (i.name.equals(name)) {

                    // Make a call to delete this item so it can be replaced
                    DB.getInstance().rmItem(i.name);

                    // Get the item location
                    String locations = i.locations;

                    // Build a new location string
                    StringBuilder sb = new StringBuilder();
                    for (String location : locations.split(",")) {

                        // Only append if the location is not the one to remove
                        if (location.equals(rmLocation)) {
                            continue;
                        }

                        sb.append(location + ",");

                    }

                    // Push new item
                    DB.getInstance().addItem(i.name, i.cost, i.quantity, i.home, sb.toString(), i.info);
                    SimpleLogger.log("IndexRoute", "Updated locations for: " + i.name);

                }
            }
        } catch (SQLException e) {
            SimpleLogger.log("IndexRoute", "Could not search for item");
        }
    }
}