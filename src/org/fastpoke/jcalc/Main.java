package org.fastpoke.jcalc;

import javax.swing.*;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        log("starting");
        final Data data = new Data(Executors.newCachedThreadPool());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log("creating wndow");
                MainWindow mainWindow = new MainWindow(data);
                data.clear();
                mainWindow.setVisible(true);
            }
        });
    }

    public static void log(String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }

}
