package Views;

import javax.swing.*;
import java.awt.*;

public class QueryView extends JDialog {
    private JButton btnSearch;
    private JTextArea txtQuery;

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
        JLabel lblEnter = new JLabel("Enter the transaction");
        txtQuery = new JTextArea();
        btnSearch = new JButton("Search");

        lblTitle.setBounds(100, 20, 600, 80);
//        lblEnter.setBounds();
//        txtQuery.setBounds();
//        btnSearch.setBounds();

        add(lblTitle);
        add(lblEnter);
        add(txtQuery);
        add(btnSearch);
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JTextArea getTxtQuery() {
        return txtQuery;
    }
}
