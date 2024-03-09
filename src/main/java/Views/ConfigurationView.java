package Views;

import Components.DialogAddFragment;
import Components.Table;

import javax.swing.*;
import java.awt.*;

public class ConfigurationView extends JDialog {
    private JLabel lblTable;
    private Table table;
    private JButton btnAddFragment, btnDeleteFragment;
    private DialogAddFragment dialogAddFragment;

    public ConfigurationView(){
        makeInterface();
    }
    
    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);

        JLabel lblTitle = new JLabel("Database configuration",JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 30));

        lblTable = new JLabel("Distributed table: Cliente");
        table = new Table(new String[]{"IDFragment", "DistributeTable", "DBMS", "Database", "Server", "Criteria", "Attributes"});
        btnAddFragment = new JButton("Add fragment");
        btnDeleteFragment = new JButton("Delete fragment");
        dialogAddFragment = new DialogAddFragment();

        lblTitle.setBounds(100, 20, 600, 80);
        lblTable.setBounds(50, 100, 700, 50);
        table.setBounds(50, 150, 700, 300);
        btnAddFragment.setBounds(150, 480, 200, 50);
        btnDeleteFragment.setBounds(450, 480, 200, 50);

        add(lblTitle);
        add(lblTable);
        add(table);
        add(btnAddFragment);
        add(btnDeleteFragment);
    }

    public String showDeleteMessage() {
        String answer = JOptionPane.showInputDialog(this, "Input the IDFragment to be deleted");
        return answer == null ? "" : answer;
    }

    public Table getTable() {
        return table;
    }

    public JButton getBtnAddFragment() {
        return btnAddFragment;
    }

    public DialogAddFragment getDialogAddFragment() {
        return dialogAddFragment;
    }

    public JButton getBtnDeleteFragment() {
        return btnDeleteFragment;
    }
}
