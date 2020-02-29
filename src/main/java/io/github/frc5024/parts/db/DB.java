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

}