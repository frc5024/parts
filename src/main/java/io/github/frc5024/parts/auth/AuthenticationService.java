package io.github.frc5024.parts.auth;

import ca.retrylife.simpleauth.SimpleAuth;
import ca.retrylife.simplelogger.SimpleLogger;

public class AuthenticationService {

    private static AuthenticationService instance = null;

    private SimpleAuth auth = new SimpleAuth();

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
        
        return false;
    }

    public boolean loggedIn() {
        return loggedIn;
    }

    public boolean hasAdmin(){
        return adminEnabled;
    }

    public void logout() {
        SimpleLogger.log("AuthenticationService", "Logged out");
        loggedIn = false;
        adminEnabled = false;
        
    }
}