package Views;

import Components.AddFragmentDialog;
import Components.Table;
import Models.Fragment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConfigurationView extends JDialog {
    private Table table;
    private JButton btnAddFragment, btnDeleteFragment;
    private AddFragmentDialog addFragmentDialog;

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

        JLabel lblTable = new JLabel("Distributed table: Clientes");
        table = new Table(new String[]{"IDFragment", "DistributedTable", "DBMS", "Database", "Server", "Criteria", "CriteriaValue", "Attributes"});
        btnAddFragment = new JButton("Add fragment");
        btnDeleteFragment = new JButton("Delete fragment");
        addFragmentDialog = new AddFragmentDialog();

        lblTitle.setBounds(100, 20, 600, 80);
        lblTable.setBounds(20, 100, 760, 50);
        table.setBounds(20, 150, 760, 300);
        btnAddFragment.setBounds(150, 480, 200, 50);
        btnDeleteFragment.setBounds(450, 480, 200, 50);

        add(lblTitle);
        add(lblTable);
        add(table);
        add(btnAddFragment);
        add(btnDeleteFragment);
    }

    public void fillTable(ArrayList<Fragment> fragments){
        table.getModel().getDataVector().removeAllElements();
        for (Fragment fragment : fragments) {
            String[] tuple = new String[table.getTable().getColumnCount()];
            tuple[0] = fragment.getIdFragment().toString();
            tuple[1] = fragment.getDistributedTable();
            tuple[2] = fragment.getDBMS();
            tuple[3] = fragment.getDB();
            tuple[4] = fragment.getURI();
            tuple[5] = fragment.getCriteria();
            tuple[6] = fragment.getCriteriaValue();
            tuple[7] = fragment.getAttributesString();
            table.getModel().addRow(tuple);
        }
        refresh();
    }

    public void refresh() {
        revalidate();
        repaint();
    }

    public String showDeleteMessage() {
        String answer = JOptionPane.showInputDialog(this, "Input the IDFragment to be deleted");
        return answer == null ? "" : answer;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public Table getTable() {
        return table;
    }

    public JButton getBtnAddFragment() {
        return btnAddFragment;
    }

    public AddFragmentDialog getAddFragmentDialog() {
        return addFragmentDialog;
    }

    public JButton getBtnDeleteFragment() {
        return btnDeleteFragment;
    }
}
