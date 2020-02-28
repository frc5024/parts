package io.github.frc5024.parts.auth;

public class AuthenticationService {

    private static AuthenticationService instance = null;

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

    public void logout() {
        
    }
}