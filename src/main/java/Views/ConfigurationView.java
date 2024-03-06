package Views;

import Components.Table;

import javax.swing.*;
import java.awt.*;

public class ConfigurationView extends JDialog {
    private JLabel lblTable;
    private Table table;

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
        table = new Table(new String[]{"Fragment", "Database", "Criteria", "Attributes"});

        lblTitle.setBounds(100, 20, 600, 80);
        lblTable.setBounds(100, 100, 600, 50);
        table.setBounds(100, 150, 600, 300);

        add(lblTitle);
        add(lblTable);
        add(table);
    }
}
