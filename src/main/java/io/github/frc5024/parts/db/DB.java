package io.github.frc5024.parts.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.Driver;

import ca.retrylife.simplelogger.SimpleLogger;

public class DB {
    private static DB instance = null;

    // DB
    private Connection conn;
    private Statement stmt;

    private DB() throws SQLException {

        // Connect to DB
        SimpleLogger.log("DB", "Connecting to DB");

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            SimpleLogger.log("DB", "Failed to register H2 Driver");
        }

        conn = DriverManager.getConnection("jdbc:h2:~/5024parts/5024partsdb2", "raider", "robotics");
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
                "create table parts(uname varchar(128), cost int, home varchar(1024), info varchar(8192))");
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

}