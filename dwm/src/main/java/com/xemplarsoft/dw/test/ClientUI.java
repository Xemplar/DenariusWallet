package com.xemplarsoft.dw.test;

import com.xemplarsoft.libs.io.JTextAreaOutputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

public class ClientUI extends JFrame implements ActionListener {
    public JPanel content = new JPanel();
    public JTextArea pane = new JTextArea();
    public JButton send = new JButton("Send");
    public JTextField input = new JTextField();

    public JTextAreaOutputStream stream;
    public DWClient cli;

    public ClientUI(DWClient cli){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }

        this.cli = cli;

        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{1, 0};
        layout.rowWeights = new double[]{1, 0};
        content.setLayout(layout);

        GridBagConstraints gbc_pane = new GridBagConstraints();
        gbc_pane.fill = GridBagConstraints.BOTH;
        gbc_pane.gridx = 0;
        gbc_pane.gridy = 0;
        gbc_pane.gridwidth = 2;
        content.add(new JScrollPane(pane), gbc_pane);
        pane.setEditable(false);

        GridBagConstraints gbc_send = new GridBagConstraints();
        gbc_send.gridx = 1;
        gbc_send.gridy = 1;
        content.add(send, gbc_send);

        GridBagConstraints gbc_input = new GridBagConstraints();
        gbc_input.fill = GridBagConstraints.HORIZONTAL;
        gbc_input.gridx = 0;
        gbc_input.gridy = 1;
        content.add(input, gbc_input);

        stream = new JTextAreaOutputStream(pane);
        System.setOut(new PrintStream(stream));

        Dimension size = new Dimension(800, 480);
        content.setMaximumSize(size);
        content.setMinimumSize(size);
        content.setPreferredSize(size);

        send.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        Thread t = new Thread(cli);
        t.start();
    }

    public void actionPerformed(ActionEvent e) {
        cli.encryptAndWrite(input.getText());
        input.setText("");
    }
}
