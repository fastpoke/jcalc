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
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = c.weighty = 1.0;
        //c.gridx = c.gridy = 2;


        final JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(Color.white);
        c.insets = new Insets(12, 12, 12, 12);
        c.gridwidth = 10;
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
        c.gridwidth = 1;
        for(int i = 0; i < 10;  i++) {
            String num;
            num = String.valueOf(i);
            JButton button = new JButton(num);
            //c.gridx = (i + 2);
            //c.gridy = (i - 1);
            button.setBackground(Color.white);
            button.setForeground(Color.black);
            button.setSize(20, 20);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log("button clicked");
                    data.increment();
                    /*
                    data.notifyAll();
                    data.fireUpdateEvent();
                    */
                }
            });
            c.gridy = 1;
            c.insets = new Insets(5, 12, 12, 12); //window toolkit
            cp.add(button, c);
        }

        pack();
    }

}
