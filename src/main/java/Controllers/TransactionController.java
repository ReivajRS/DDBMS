package Controllers;

import Models.Database;
import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.TransactionView;
import java.awt.event.*;

public class TransactionController implements ActionListener {
    private TransactionView transactionView;

    public TransactionController(TransactionView transactionView, Database database) {
        this.transactionView = transactionView;
    }

    public void setVisible(boolean visible){
        transactionView.setVisible(visible);
    }

    private void setListeners() {
        transactionView.getBtnExecute().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == transactionView.getBtnExecute()){
            String transaction = transactionView.getTxtTransaction().getText();
            System.out.println(transaction);
            return;
        }
    }
}
