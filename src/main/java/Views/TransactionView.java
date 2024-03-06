package Views;

import javax.swing.*;
import java.awt.*;

public class TransactionView extends JDialog {

    private JButton btnExecute;
    private JTextArea txtTransaction;
    public TransactionView(){
        makeInterface();

    }

    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);

        JLabel lblTitle = new JLabel("Transaction",JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel lblEnter = new JLabel("Enter the query");
        txtTransaction = new JTextArea();
        btnExecute = new JButton("Execute");

        lblTitle.setBounds(100, 20, 600, 80);
//        lblEnter.setBounds();
//        txtTransaction.setBounds();
//        btnExecute.setBounds();

        add(lblTitle);
        add(lblEnter);
        add(txtTransaction);
        add(btnExecute);
    }

    public JButton getBtnExecute() {
        return btnExecute;
    }

    public JTextArea getTxtTransaction() {
        return txtTransaction;
    }
}
