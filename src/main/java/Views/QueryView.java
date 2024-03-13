package Views;

import Components.Table;
import Models.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QueryView extends JDialog {
    private JButton btnSearch;
    private JTextArea txtQuery;
    private Table table;

    public QueryView(){
        makeInterface();
    }

    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);

        JLabel lblTitle = new JLabel("Query",JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel lblEnter = new JLabel("Enter the query",JLabel.CENTER);
        table = new Table(new String[]{"idCliente","Nombre","Estado","Credito","Deuda"});
        txtQuery = new JTextArea();
        txtQuery.setFont(new Font("tahoma", Font.PLAIN, 14));
        lblEnter.setFont(new Font("Serif", Font.PLAIN, 18));
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

    public void fillTable(ArrayList<Cliente> customers) {
        table.getModel().getDataVector().removeAllElements();
        for (Cliente customer : customers) {
            String[] tuple = new String[table.getTable().getColumnCount()];
            tuple[0] = customer.getIdcliente() == null ? "" :customer.getIdcliente()+"";
            tuple[1] = customer.getNombre() == null ? "" : customer.getNombre();
            tuple[2] = customer.getEstado() == null ? "" : customer.getEstado();
            tuple[3] = customer.getCredito() == null ? "" : customer.getCredito()+"";
            tuple[4] = customer.getDeuda() == null ? "" : customer.getDeuda()+"";
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

    public Table getTable(){return table;}
}
