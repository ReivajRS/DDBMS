package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFragmentDialog extends JDialog {
    private JButton btnAdd, btnClear;
    private JTextField txtTable, txtDB, txtServer, txtUser, txtPassword, txtCriteria, txtCriteriaValue;
    private JTextField[] txtAttributes;
    private JComboBox<String> comboDBMS;

    public AddFragmentDialog() {
        makeInterface();
    }
    
    private void makeInterface() {
        setTitle("Add fragment");
        setSize(300, 400);
        setLayout(new GridLayout(0,2));
        setResizable(false);
        setLocationRelativeTo(this);
        setModal(true);

        txtTable = new JTextField();
        comboDBMS = new JComboBox<>(new String[]{"Select", "SQL Server", "Neo4j", "Mongo DB"});
        txtDB = new JTextField();
        txtServer = new JTextField();
        txtUser = new JTextField();
        txtPassword = new JTextField();
        txtCriteria = new JTextField();
        txtCriteriaValue = new JTextField();

        txtAttributes = new JTextField[5];
        for (int i = 0; i < txtAttributes.length; i++)
            txtAttributes[i] = new JTextField();

        btnAdd = new JButton("Add");
        btnClear = new JButton("Clear");

        add(new JLabel("Distributed table: ", JLabel.CENTER));
        add(txtTable);
        add(new JLabel("DBMS: ", JLabel.CENTER));
        add(comboDBMS);
        add(new JLabel("Database: ", JLabel.CENTER));
        add(txtDB);
        add(new JLabel("Server: ", JLabel.CENTER));
        add(txtServer);
        add(new JLabel("User: ", JLabel.CENTER));
        add(txtUser);
        add(new JLabel("Password: ", JLabel.CENTER));
        add(txtPassword);
        add(new JLabel("Criteria: ", JLabel.CENTER));
        add(txtCriteria);
        add(new JLabel("Criteria value: ", JLabel.CENTER));
        add(txtCriteriaValue);
        add(new JLabel("Attributes", JLabel.CENTER));
        add(new JLabel(""));
        for (int i = 0; i < txtAttributes.length; i++) {
            add(new JLabel("#" + (i + 1), JLabel.CENTER));
            add(txtAttributes[i]);
        }

        add(btnAdd);
        add(btnClear);
    }

    public void clear() {
        txtTable.setText("");
        comboDBMS.setSelectedIndex(0);
        txtDB.setText("");
        txtServer.setText("");
        txtUser.setText("");
        txtPassword.setText("");
        txtCriteria.setText("");
        txtCriteriaValue.setText("");
        for (JTextField txt : txtAttributes)
            txt.setText("");
        txtTable.requestFocus();
    }
    
    public String[] getValues() {
        String[] values = new String[9];
        values[0] = txtTable.getText();
        values[1] = comboDBMS.getSelectedIndex() == 0 ? "" : comboDBMS.getItemAt(comboDBMS.getSelectedIndex());
        values[2] = txtDB.getText();
        values[3] = txtServer.getText();
        values[4] = txtUser.getText();
        values[5] = txtPassword.getText();
        values[6] = txtCriteria.getText();
        values[7] = txtCriteriaValue.getText();
        StringBuilder attributes = new StringBuilder();
        boolean flag = false;
        for (JTextField txt : txtAttributes) {
            if (txt.getText().isBlank())
                flag = true;
            if (!attributes.isEmpty())
                attributes.append(",");
            attributes.append(txt.getText());
        }
        values[8] = flag ? "" : attributes.toString();
        return values;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnClear() {
        return btnClear;
    }
}
