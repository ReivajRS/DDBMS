package Views;

import javax.swing.*;
import java.awt.*;

public class TransactionView extends JDialog {

    private JButton btnExecute;
    private JTextArea txtTransaction;
    private JLabel lblTitle, lblEnter;

    public TransactionView(){
        makeInterface();
        setStyles();
    }

    private void makeInterface() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);

        lblTitle = new JLabel("Transactions",JLabel.CENTER);
        lblEnter = new JLabel("Enter the transaction",JLabel.CENTER);
        txtTransaction = new JTextArea();
        btnExecute = new JButton("Execute");

        lblTitle.setBounds(100, 20, 600, 80);
        lblEnter.setBounds(100,120,600,30);
        txtTransaction.setBounds(100,170,600,80);
        btnExecute.setBounds(100,270,600,80);

        add(lblTitle);
        add(lblEnter);
        add(txtTransaction);
        add(btnExecute);
    }

    public void setStyles() {
        Color[] colors = new Color[]{new Color(249, 247, 247), new Color(63, 114, 175), new Color(17, 45, 78)};

        lblTitle.setFont(new Font("calibri", Font.BOLD, 50));
        lblEnter.setFont(new Font("calibri", Font.PLAIN, 20));
        txtTransaction.setFont(new Font("calibri", Font.PLAIN, 16));
        Font font = new Font("calibri", Font.BOLD, 24);
        btnExecute.setFont(font);

        getContentPane().setBackground(colors[0]);
        btnExecute.setBackground(colors[1]);

        btnExecute.setForeground(colors[0]);
        lblTitle.setForeground(colors[2]);

        txtTransaction.setBorder(BorderFactory.createLineBorder(colors[2]));
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
