package org.fastpoke.jcalc;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public abstract class AConverterDocumentListener extends ADocumentListener {

    protected final JTextField output;

    public AConverterDocumentListener(JTextComponent input, JTextField output) {
        super(input);
        this.output = output;
    }

    @Override
    protected final void onUpdate() {
        String text = input.getText().trim();
        if (text.isEmpty()) {
            output.setText("");
            return;
        }
        long value;
        try {
            value = Long.parseLong(text);
        } catch (NumberFormatException e) {
            output.setText("NaNNaNNaNNaN Batman!");
            return;
        }
        output.setText(convert(value));
    }

    protected abstract String convert(long value);

}
