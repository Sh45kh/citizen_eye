package com.graduation.project.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@Component // This tells Spring to load this class automatically
public class BrowserLauncher {

    // This listens for the exact moment the application is 100% ready
    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        String url = "http://localhost:8080/";

        // Sometimes Java's desktop tool can be a little stubborn,
        // so we wrap it in a try-catch block to prevent crashes if it fails.
        try {
            // Method 1: The standard Java Desktop approach
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
            // Method 2: The native Linux fallback
            else {
                Runtime.getRuntime().exec("xdg-open " + url);
            }
        } catch (Exception e) {
            System.out.println("Could not open browser automatically. Please visit: " + url);
        }
    }
}