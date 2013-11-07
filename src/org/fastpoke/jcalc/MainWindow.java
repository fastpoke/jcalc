package org.fastpoke.jcalc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("jCalc v0.2.0");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        JTextArea text = addTextField(cp, c);
        addResultField(cp, c, text);
        addDescription(cp, c);

        pack();
    }

    private JTextArea addTextField(Container cp, GridBagConstraints c) {
        JTextArea text = new JTextArea(4, 0);
        text.setLineWrap(true);
        c.insets = new Insets(12, 12, 3, 12);
        c.weighty = 1.0;
        c.gridx = c.gridy = 0;
        cp.add(new JScrollPane(text), c);
        return text;
    }

    private void addResultField(Container cp, GridBagConstraints c, final JTextArea text) {
        final JTextField result = new JTextField();
        c.insets = new Insets(3, 12, 3, 12);
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onUpdate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onUpdate();
            }

            private void onUpdate() {
                String resultString;
                try {
                    double value = Parser.parse(text.getText());
                    if (value == (long) value) {
                        resultString = String.valueOf((long) value);
                    } else {
                        resultString = String.valueOf(value);
                    }
                } catch (ParserException e) {
                    resultString = e.getMessage();
                }
                result.setText(resultString);
            }
        });
        cp.add(result, c);
    }

    private void addDescription(Container cp, GridBagConstraints c) {
        final JLabel description = new JLabel(
                "<html>Welcome to jCalc!<br/>" +
                        "<ul>" +
                        "<li>sqrt</li>" +
                        "<li>desu</li>" +
                        "</ul>" +
                        "Are you enjoying your time of JCALC?</html>"
        );
        c.insets = new Insets(3, 12, 12, 12);
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;
        cp.add(description, c);
    }

}
