package org.fastpoke.jcalc;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        log("starting");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log("creating window");
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
            }
        });
    }

    public static void log(String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }

}
