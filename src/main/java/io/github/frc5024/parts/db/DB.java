package io.github.frc5024.parts.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.h2.Driver;

import ca.retrylife.simplelogger.SimpleLogger;

public class DB {
    private static DB instance = null;

    // DB
    private Connection conn;
    private Statement stmt;

    public class UserInfo {
        public String username;
        public String hash;
        public int perm;
    }

    public class ItemInfo {
        public String name;
        public int cost;
        public int quantity;
        public String home;
        public String locations;
        public String info;
    }

    private DB() throws SQLException {

        // Connect to DB
        SimpleLogger.log("DB", "Connecting to DB");

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            SimpleLogger.log("DB", "Failed to register H2 Driver");
        }

        conn = DriverManager.getConnection("jdbc:h2:~/5024parts/5024partsdb2;AUTO_SERVER=TRUE", "raider", "robotics");
        conn.setAutoCommit(true);
        SimpleLogger.log("DB", "Connected");

        stmt = conn.createStatement();
        SimpleLogger.log("DB", "Created statement");

    }

    public static DB getInstance() throws SQLException {
        if (instance == null) {
            instance = new DB();
        }

        return instance;
    }

    /**
     * Init the DB (KILLS OLD DB!)
     * 
     * @throws SQLException
     */
    public void initDB() throws SQLException {

        // Drop old tables
        try {
            stmt.execute("drop table user");
            stmt.execute("drop table parts");
            SimpleLogger.log("DB", "Dropped tables");
        } catch (SQLException e) {
            SimpleLogger.log("DB", "No tables to drop, dropping none");
        }

        // Create tables
        stmt.execute("create table user(uname varchar(128), hash varchar(128), permissions int)");
        stmt.execute(
                "create table parts(uname varchar(128), cost int, quantity int, home varchar(1024), knownlocs varchar(4096), info varchar(8192))");
        SimpleLogger.log("DB", "Built tables");

        conn.commit();
        SimpleLogger.log("DB", "Wrote new table");
    }

    /**
     * Add a user
     * 
     * @param username   Username
     * @param hash       User password hash
     * @param permission User permission int
     */
    public void addUser(String username, String hash, int permission) throws SQLException {
        stmt.execute(String.format("insert into user values('%s', '%s', %d)", username, hash, permission));
    }

    /**
     * Add an item
     * 
     * @param name      Item name
     * @param cost      Item cost
     * @param quantity  Item quantity
     * @param home      Item home
     * @param locations Item locations
     * @param info      Item info
     * @throws SQLException
     */
    public void addItem(String name, int cost, int quantity, String home, String locations, String info)
            throws SQLException {
        stmt.execute(String.format("insert into parts values('%s', %d, %d, '%s', '%s', '%s')", name, cost, quantity,
                home, locations, info));
    }

    /**
     * Get the password hash for a user
     * 
     * @param username Username
     * @return Password hash
     */
    public String getUserHash(String username) throws SQLException {
        ResultSet users = stmt.executeQuery("select * from user");

        while (users.next()) {

            // Check if user matches username
            if (users.getString("uname").equals(username)) {
                return users.getString("hash");
            }
        }

        return null;

    }

    /**
     * Get a list of all users
     * 
     * @return All users
     * @throws SQLException
     */
    public ArrayList<UserInfo> getAllUserInfo() throws SQLException {
        ResultSet users = stmt.executeQuery("select * from user");

        ArrayList<UserInfo> output = new ArrayList<>();

        while (users.next()) {

            // Add output
            UserInfo u = new UserInfo();
            u.username = users.getString("uname");
            u.hash = users.getString("hash");
            u.perm = users.getInt("permissions");
            output.add(u);
        }

        return output;

    }

    /**
     * Get a list of all items
     * 
     * @return All items
     * @throws SQLException
     */
    public ArrayList<ItemInfo> getAllItemInfo() throws SQLException {
        ResultSet parts = stmt.executeQuery("select * from parts");

        ArrayList<ItemInfo> output = new ArrayList<>();

        while (parts.next()) {

            // Add output
            ItemInfo p = new ItemInfo();
            p.name = parts.getString("uname");
            p.cost = parts.getInt("cost");
            p.quantity = parts.getInt("quantity");
            p.home = parts.getString("home");
            p.locations = parts.getString("knownlocs");
            p.info = parts.getString("info");
            output.add(p);
        }

        return output;

    }

    /**
     * Get the user permissions
     * 
     * @param username Username
     * @return Permissions key
     * @throws SQLException
     */
    public int getUserPerms(String username) throws SQLException {
        ResultSet users = stmt.executeQuery("select * from user");

        while (users.next()) {

            // Check if user matches username
            if (users.getString("uname").equals(username)) {
                return users.getInt("permissions");
            }
        }

        return 0;
    }

    public void close() throws SQLException {
        stmt.close();
        conn.close();
        SimpleLogger.log("DB", "Closed");

    }

    /**
     * Delete a user from the DB
     * 
     * @param username Username
     * @throws SQLException
     */
    public void rmUser(String username) throws SQLException {
        SimpleLogger.log("DB", "Deleting " + username);
        stmt.execute(String.format("delete from user where uname='%s'", username));
    }

    /**
     * Remove an item
     * 
     * @param name Item name
     * @throws SQLException
     */
    public void rmItem(String name) throws SQLException {
        SimpleLogger.log("DB", "Deleting " + name);
        stmt.execute(String.format("delete from parts where uname='%s'", name));
    }

}