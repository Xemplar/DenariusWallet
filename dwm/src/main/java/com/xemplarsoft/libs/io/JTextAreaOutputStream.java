package com.xemplarsoft.libs.io;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.IOException;
import java.io.OutputStream;

public class JTextAreaOutputStream extends OutputStream {
    private JTextArea pane;
    public JTextAreaOutputStream(JTextArea pane){
        this.pane = pane;
    }

    @Override
    public void write(int b) throws IOException {
        pane.setText(pane.getText().replaceAll("\r", ""));
        pane.setText(pane.getText() + ((char) b));

        Document d = pane.getDocument();
        pane.select(d.getLength(), d.getLength());
    }

    @Override
    public void write(byte[] b) throws IOException {
        pane.setText(pane.getText().replaceAll("\r", ""));
        pane.setText(pane.getText() + new String(b));

        Document d = pane.getDocument();
        pane.select(d.getLength(), d.getLength());
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        pane.setText(pane.getText().replaceAll("\r", ""));

        String dat = new String(b);
        pane.setText(pane.getText() + dat.substring(off, off + len));

        Document d = pane.getDocument();
        pane.select(d.getLength(), d.getLength());
    }

    public void write(String str) throws IOException{
        write(str.getBytes(), 0, str.length());
    }
}
