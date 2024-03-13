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

        JLabel lblTitle = new JLabel("Transactions",JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
        JLabel lblEnter = new JLabel("Enter the transaction",JLabel.CENTER);
        txtTransaction = new JTextArea();
        btnExecute = new JButton("Execute");

        lblTitle.setBounds(100, 20, 600, 80);
        lblEnter.setBounds(100,120,600,30);
        txtTransaction.setBounds(100,170,600,80);
        btnExecute.setBounds(100,270,600,80);

        txtTransaction.setFont(new Font("tahoma", Font.PLAIN, 14));
        lblEnter.setFont(new Font("Serif", Font.PLAIN, 18));

        add(lblTitle);
        add(lblEnter);
        add(txtTransaction);
        add(btnExecute);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton getBtnExecute() {
        return btnExecute;
    }

    public JTextArea getTxtTransaction() {
        return txtTransaction;
    }
}
