package org.fastpoke.jcalc;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("jCalc v0.2.0");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Calculator", createCalcPane());
        tabbedPane.addTab("Conversion", createConversionPane());
        getContentPane().add(tabbedPane);

        pack();
    }

    private JComponent createCalcPane() {
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        JTextArea text = addTextField(pane, c);
        addResultField(pane, c, text);
        addDescription(pane, c);
        return pane;
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

    private void addResultField(Container cp, GridBagConstraints c, JTextArea text) {
        final JTextField result = new JTextField();
        c.insets = new Insets(3, 12, 3, 12);
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        text.getDocument().addDocumentListener(new ADocumentListener(text) {
            @Override
            protected void onUpdate() {
                String resultString;
                try {
                    double value = Parser.parse(text.getText());
                    if (value == (long) value) {
                        resultString = Long.toString((long) value);
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
                        "<li>sqrt(a) - square root of a</li>" +
                        "<li>pow(a, b) - raise a to power b</li>" +
                        "</ul>" +
                        "Are you enjoying your time of JCALC?</html>"
        );
        c.insets = new Insets(3, 12, 12, 12);
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;
        cp.add(description, c);
    }

    private JComponent createConversionPane() {
        JComponent pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField text = addInput(pane, c);
        c.gridwidth = 1;
        addBinary(pane, c, text);
        addOctocat(pane, c, text); //cat^8, lol
        addHex(pane, c, text);
        return pane;
    }

    private JTextField addInput(JComponent pane, GridBagConstraints c) {
        JTextField text = new JTextField();
        c.insets = new Insets(12, 12, 3, 12);
        //c.weighty = 1.0;
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        pane.add(text, c);
        return text;
    }

    private void addBinary(JComponent pane, GridBagConstraints c, JTextField text) {
        JLabel label = new JLabel("Binary:");
        c.insets = new Insets(3, 12, 3, 3);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        pane.add(label, c);

        final JTextField field = new JTextField();
        c.insets = new Insets(3, 3, 3, 12);
        c.gridx = 1;
        c.weightx = 1;
        text.getDocument().addDocumentListener(new ADocumentListener(text) {
            @Override
            protected void onUpdate() {
                field.setText(Long.toBinaryString(Long.parseLong(text.getText())));
            }
        });
        pane.add(field, c);
    }

    private void addOctocat(JComponent pane, GridBagConstraints c, JTextField text) {
        JLabel label = new JLabel("Octocat:");
        c.insets = new Insets(3, 12, 3, 3);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        pane.add(label, c);

        final JTextField field = new JTextField();
        c.insets = new Insets(3, 3, 3, 12);
        c.gridx = 1;
        c.weightx = 1;
        text.getDocument().addDocumentListener(new ADocumentListener(text) {
            @Override
            protected void onUpdate() {
                field.setText(Long.toOctalString(Long.parseLong(text.getText())));
            }
        });
        pane.add(field, c);
    }

    private void addHex(JComponent pane, GridBagConstraints c, JTextField text) {
        JLabel label = new JLabel("Hex:");
        c.insets = new Insets(3, 12, 12, 3);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        pane.add(label, c);
        final JTextField field = new JTextField();
        c.insets = new Insets(3, 3, 12, 12);
        c.gridx = 1;
        c.weightx = 1;
        text.getDocument().addDocumentListener(new ADocumentListener(text) {
            @Override
            protected void onUpdate() {
                field.setText(Long.toHexString(Long.parseLong(text.getText())));
            }
        });
        pane.add(field, c);
    }
}
