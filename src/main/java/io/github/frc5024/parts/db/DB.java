package io.github.frc5024.parts.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ca.retrylife.simplelogger.SimpleLogger;

public class DB {
    private static DB instance = null;

    // DB
    private Connection conn;
    private Statement stmt;

    private DB() throws SQLException {

        // Connect to DB
        SimpleLogger.log("DB", "Connecting to DB");

        conn = DriverManager.getConnection("jdbc:h2:5024partsdb", "raider", "robotics");
        SimpleLogger.log("DB", "Connected");

        stmt = conn.createStatement();       

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
        stmt.execute("drop table users");
        stmt.execute("drop table parts");

        // Create tables
        stmt.execute("create table user(uname varchar(128) primary key, hash varchar(128), permissions int)");
        stmt.execute(
                "create table parts(uname varchar(128) primary key, cost int, home varchar(1024), info varchar(8192))");
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
        ResultSet users = stmt.executeQuery("select * from users");

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
        ResultSet users = stmt.executeQuery("select * from users");

        while (users.next()) {

            // Check if user matches username
            if (users.getString("uname").equals(username)) {
                return users.getInt("permissions");
            }
        }

        return 0;
    }

}