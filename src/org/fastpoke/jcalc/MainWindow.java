package org.fastpoke.jcalc;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("jCalc v0.3.2");
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
                    double value = Parser.parse(input.getText());
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
                "<html>" +
                        "<b>Welcome to jCalc (line-parse calculator)~</b><br/>" +
                        "<table>" +
                        "<tr><td><font color=#ff0000><b>Functions:</b></font></tr></td>" +
                        "<tr><td>addition:</td><td><font face=\"Droid Sans Mono\">(a + b)</font></td></tr>" +
                        "<tr><td>subtraction:</td><td><font face=\"Droid Sans Mono\">(a - b)</font></td></tr>" +
                        "<tr><td>multiplication:</td><td><font face=\"Droid Sans Mono\">(a * b)</font></td></tr>" +
                        "<tr><td>division:</td><td><font face=\"Droid Sans Mono\">(a / b)</font></td></tr>" +
                        "<tr><td>division with remainder:</td><td><font face=\"Droid Sans Mono\">(a % b)</font></td></tr>" +
                        "<tr><td>square root of a:</td><td><font face=\"Droid Sans Mono\">sqrt(a)</font></td></tr>" +
                        "<tr><td>raise a to power b:</td><td><font face=\"Droid Sans Mono\">pow(a, b)</font></td></tr>" +
                        "<tr><td>sin a°</td><td><font face=\"Droid Sans Mono\">sin(a)</font></td></tr>" +
                        "<tr><td>cos a°</td><td><font face=\"Droid Sans Mono\">cos(a)</font></td></tr>" +
                        "<tr><td>tg a°</td><td><font face=\"Droid Sans Mono\">tg(a)</font></td></tr>" +
                        "<tr><td>log of the number b to the base a:</td>" +
                        "<td><font face=\"Droid Sans Mono\">log(a, b)</font></td></tr>" +
                        "<tr><td>log10 from a:</td><td><font face=\"Droid Sans Mono\">lg(a)</font></td></tr>" +
                        "<tr><td>natural log from a:</td><td><font face=\"Droid Sans Mono\">ln(a)</font></td></tr>" +
                        "<tr><td></td><tr>" +
                        "<tr><td><b><font color=#ff0000>example:</font></b></td>" +
                        "<td><font face=\"Droid Sans Mono\">(1 + 2) / (4 - 2) * 2 = 3</font></td></tr>" +
                        "<tr><td><b><font color=#ff0000>nested operations:</font></b></td>" +
                        "<td><font face=\"Droid Sans Mono\">log(3, (((4 + 2) / (2 +1)) * 4 + 1 )) = 2</font></td></tr>" +
                        "</table>" +
                        "<br/>" +
                        "<i><font size=3><center>Are you enjoying the time of jCalc?</center></font></i>" +
                        "</html>"
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

        JTextField field = new JTextField();
        field.setEditable(false);
        c.insets = new Insets(3, 3, 3, 12);
        c.gridx = 1;
        c.weightx = 1;
        text.getDocument().addDocumentListener(new AConverterDocumentListener(text, field) {
            @Override
            protected String convert(long value) {
                return Long.toBinaryString(value);
            }
        });
        pane.add(field, c);
    }

    private void addOctocat(JComponent pane, GridBagConstraints c, JTextField text) {
        JLabel label = new JLabel("Octocat:");  //cat^8
        c.insets = new Insets(3, 12, 3, 3);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        pane.add(label, c);

        final JTextField field = new JTextField();
        field.setEditable(false);
        c.insets = new Insets(3, 3, 3, 12);
        c.gridx = 1;
        c.weightx = 1;
        text.getDocument().addDocumentListener(new AConverterDocumentListener(text, field) {
            @Override
            protected String convert(long value) {
                return Long.toOctalString(value);
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
        field.setEditable(false);
        c.insets = new Insets(3, 3, 12, 12);
        c.gridx = 1;
        c.weightx = 1;
        text.getDocument().addDocumentListener(new AConverterDocumentListener(text, field) {
            @Override
            protected String convert(long value) {
                return Long.toHexString(value);
            }
        });
        pane.add(field, c);
    }
}
