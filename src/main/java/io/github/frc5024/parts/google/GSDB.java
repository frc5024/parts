package io.github.frc5024.parts.google;

public class GSDB {
    private static GSDB instance;

    private GSDB() {

    }
    
    public static GSDB getInstance(){
        if (instance == null) {
            instance = new GSDB();
        }

        return instance;
    }

    public GSTable getTable(String name) {
        return null;
    }
}