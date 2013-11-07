package org.fastpoke.jcalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.fastpoke.jcalc.Main.log;

public class MainWindow extends JFrame {

    public MainWindow(final Data data) {
        super("jCalc v0.1.1");
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = c.weighty = 1.0;

        addTextField(data, cp, c);

        c.gridwidth = 1;
        c.insets = new Insets(6, 10, 6, 10); //window toolkit

        //addDigitButtons(data, cp, c);
        addSolveButton(data, cp, c);
        addClearButton(data, cp, c);

        pack();
    }

//methods

    private void addTextField(final Data data, Container cp, GridBagConstraints c) {
        final JTextField textField = new JTextField();
        //textField.setEditable(false);
        textField.setBackground(Color.white);
        c.insets = new Insets(16, 10, 8, 10);
        c.gridwidth = 18;
        cp.add(textField, c);
        data.addListener(new Runnable() {
            @Override
            public void run() {
                log("on data change");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        log("setting text in EDT");  //Event Dispatch Thread
                        textField.setText(String.valueOf(data.getValue()));
                    }
                });
            }
        });
    }

    /*
    private void addDigitButtons(final Data data, Container cp, GridBagConstraints c) {
        for(int i = 0; i <= 9; i++) {
            c.gridx = (i - 1) % 3;
            c.gridy = 4 - ((i + 2) / 3) % 4;

            final int digit = i % 10;
            JButton button = new JButton(String.valueOf(digit));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log("button clicked: " + digit);
                    data.append(digit);
                }
            });
            cp.add(button, c);
        }
    }
    */

    private void addSolveButton(final Data data, Container cp, GridBagConstraints c) {
        JButton button = new JButton("Honk!");
        c.gridx = 1;
        c.gridy = 3;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log("Honk!");
                data.append(0);
            }
        });
        cp.add(button, c);
    }


    private void addClearButton(final Data data, Container cp, GridBagConstraints c) {
        JButton button = new JButton("Clear");
        c.gridx = 2;
        c.gridy = 3;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log("clear");
                data.clear();
            }
        });
        cp.add(button, c);
    }

}
