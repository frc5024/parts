package io.github.frc5024.parts.auth;

import java.sql.SQLException;

import ca.retrylife.simpleauth.SimpleAuth;
import ca.retrylife.simplelogger.SimpleLogger;
import io.github.frc5024.parts.db.DB;

public class AuthenticationService {

    private static AuthenticationService instance = null;

    public SimpleAuth auth = new SimpleAuth();

    // Auth status
    private boolean loggedIn, adminEnabled = false;
    

    private AuthenticationService() {

    }

    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }

        return instance;
    }

    public boolean login(String user, String passwd) {

        // Check if the user exists
        try {

            // Get user info
            String hash = DB.getInstance().getUserHash(user);
            int permissions = DB.getInstance().getUserPerms(user);

            // Check user password
            if (auth.isValid(passwd, hash)) {

                SimpleLogger.log("AuthenticationService", user + " has logged in");
                
                // Set user perms
                loggedIn = true;
                adminEnabled = (permissions == 1);

                return true;
            }
        } catch (SQLException e) {
            SimpleLogger.log("AuthenticationService", "SQL Error");
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public boolean loggedIn() {
        return loggedIn;
    }

    public boolean hasAdmin() {
        return adminEnabled;
    }

    public void logout() {
        SimpleLogger.log("AuthenticationService", "Logged out");
        loggedIn = false;
        adminEnabled = false;

    }
}