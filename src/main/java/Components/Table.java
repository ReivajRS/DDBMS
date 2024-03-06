package Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Table extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;

    public Table(String[] columnHeaders) {
        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnHeaders);
        table.setModel(model);
        setViewportView(table);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
