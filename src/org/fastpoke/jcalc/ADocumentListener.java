package org.fastpoke.jcalc;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public abstract class ADocumentListener implements DocumentListener {

    protected final JTextComponent text;

    public ADocumentListener(JTextComponent text) {
        this.text = text;
    }

    @Override
    public final void insertUpdate(DocumentEvent e) {
        onUpdate();
    }

    @Override
    public final void removeUpdate(DocumentEvent e) {
        onUpdate();
    }

    @Override
    public final void changedUpdate(DocumentEvent e) {
        onUpdate();
    }

    protected abstract void onUpdate();

}
