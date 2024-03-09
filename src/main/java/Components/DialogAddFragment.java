package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogAddFragment extends JDialog implements ActionListener {
    private JButton btnAdd, btnClear;
    private JTextField txtTable, txtDBMS, txtDB, txtServer, txtCriteria, txtAttributes;
    public DialogAddFragment() {
        makeInterface();
        setListeners();
    }
    
    private void makeInterface() {
        setTitle("Add fragment");
        setSize(300, 400);
        setLayout(new GridLayout(0,2));
        setResizable(false);
        setLocationRelativeTo(this);
        setModal(true);

        txtTable = new JTextField();
        txtDBMS = new JTextField();
        txtDB = new JTextField();
        txtServer = new JTextField();
        txtCriteria = new JTextField();
        txtAttributes = new JTextField();

        btnAdd = new JButton("Add");
        btnClear = new JButton("Clear");

        add(new JLabel("Distributed table: ", JLabel.CENTER));
        add(txtTable);
        add(new JLabel("DBMS: ", JLabel.CENTER));
        add(txtDBMS);
        add(new JLabel("Database: ", JLabel.CENTER));
        add(txtDB);
        add(new JLabel("Server: ", JLabel.CENTER));
        add(txtServer);
        add(new JLabel("Criteria: ", JLabel.CENTER));
        add(txtCriteria);
        add(new JLabel("Attributes: ", JLabel.CENTER));
        add(txtAttributes);

        add(btnAdd);
        add(btnClear);
    }

    private void setListeners() {
        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
    }


    public void clear() {
        txtTable.setText("");
        txtDBMS.setText("");
        txtDB.setText("");
        txtServer.setText("");
        txtCriteria.setText("");
        txtAttributes.setText("");
        txtTable.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            clear();
            return;
        }
        if (e.getSource() == btnAdd) {
            // TODO: Add the fragment to DBConfiguration
            System.out.println("Fragmento añadido - PENDIENTE");
        }
    }
}
