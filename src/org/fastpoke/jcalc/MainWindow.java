package org.fastpoke.jcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.fastpoke.jcalc.Main.log;

public class MainWindow extends JFrame {

    public MainWindow(final Data data) {
        super("jCalc");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = c.weighty = 1.0;

        final JTextField textField = new JTextField();
        textField.setEditable(false);
        c.insets = new Insets(12, 12, 12, 0);
        cp.add(textField, c);
        data.addListener(new Runnable() {
            @Override
            public void run() {
                log("on data change");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        log("setting text in EDT");  //EDT - Event Dispatch Thread
                        textField.setText(String.valueOf(data.getValue()));
                    }
                });
            }
        });

        for(int i = 0; i < 10;  i++) {
            String num;
            num = String.valueOf(i);
            JButton button = new JButton(num);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log("button clicked");
                    data.increment();
                }
            });
            c.gridy = 1;
            c.insets = new Insets(2, 12, 12, 12); //AWT - window toolkit
            cp.add(button, c);
        }

        pack();
    }

}
