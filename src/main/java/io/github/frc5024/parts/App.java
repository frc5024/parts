package io.github.frc5024.parts;

import ca.retrylife.simplelogger.SimpleLogger;

public class App implements Runnable{
    public static void main(String[] args) {
        (new App()).run();
    }

    private App() {
        
    }

    public void run() {
        SimpleLogger.log("App", "Starting");

    }
}
