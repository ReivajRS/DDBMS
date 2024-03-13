package Views;

import Components.Table;
import Models.Cliente;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class QueryView extends JDialog {
    private JButton btnSearch;
    private JTextArea txtQuery;
    private Table table;
    private JLabel lblTitle, lblEnter;


    public QueryView(){
        makeInterface();
        setStyles();
    }

    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);

        lblTitle = new JLabel("Query",JLabel.CENTER);
        lblEnter = new JLabel("Enter the query",JLabel.CENTER);

        table = new Table(new String[]{"idCliente","Nombre","Estado","Credito","Deuda"});
        txtQuery = new JTextArea();
        btnSearch = new JButton("Search");


        lblTitle.setBounds(100, 20, 600, 60);
        lblEnter.setBounds(100,90,600,30);

        txtQuery.setBounds(100,130,600,60);
        btnSearch.setBounds(100,200,600,40);

        table.setBounds(100,250,600,300);

        add(lblTitle);
        add(lblEnter);
        add(txtQuery);
        add(btnSearch);
        add(table);
    }

    public void setStyles() {
        Color[] colors = new Color[]{new Color(249, 247, 247), new Color(63, 114, 175), new Color(17, 45, 78)};

        lblTitle.setFont(new Font("calibri", Font.BOLD, 50));
        lblEnter.setFont(new Font("calibri", Font.PLAIN, 20));
        txtQuery.setFont(new Font("calibri", Font.PLAIN, 16));
        Font font = new Font("calibri", Font.BOLD, 24);
        btnSearch.setFont(font);

        btnSearch.setForeground(colors[0]);
        lblTitle.setForeground(colors[2]);

        getContentPane().setBackground(colors[0]);
        btnSearch.setBackground(colors[1]);

        txtQuery.setBorder(BorderFactory.createLineBorder(colors[2]));
    }

    private Object[] getAttributes(Cliente customer) {
        return new Object[]{
                customer.getIdcliente(), customer.getNombre(),
                customer.getEstado(), customer.getCredito(),
                customer.getDeuda()
        };
    }

    public void fillTable(ArrayList<Cliente> customers) {
        table.getModel().getDataVector().removeAllElements();

        if (customers.isEmpty()) return;

        Object[] firstClientAttributes = getAttributes(customers.getFirst());
        String[] headers = {"IdCliente", "Nombre", "Estado", "Credito", "Deuda"};

        Vector<String> columnHeaders = new Vector<>();
        for (int i = 0; i < 5; i++)
            if (firstClientAttributes[i] != null)
                columnHeaders.add(headers[i]);
        table.getModel().setColumnIdentifiers(columnHeaders);

        for (Cliente customer : customers) {
            Object[] clientAttributes = getAttributes(customer);
            String[] tuple = new String[table.getTable().getColumnCount()];
            for (int i = 0, j = 0; i < 5; i++)
                if (clientAttributes[i] != null)
                    tuple[j++] = clientAttributes[i].toString(); //... ha quedado
            table.getModel().addRow(tuple);
        }
        refresh();
        showMessage("Refreshed table");
    }

    public void refresh() {
        revalidate();
        repaint();
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JTextArea getTxtQuery() {
        return txtQuery;
    }
}
