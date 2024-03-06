package Controllers;

import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.TransactionView;
import java.awt.event.*;

public class TransactionController implements ActionListener {
    private TransactionView transactionView;
    private MongoDB mongoDB;
    private Neo4j neo4j;
    private SQLServer sqlServer;

    public TransactionController(TransactionView transactionView, MongoDB mongoDB, Neo4j neo4j, SQLServer sqlServer) {
        this.transactionView = transactionView;
        this.mongoDB = mongoDB;
        this.neo4j = neo4j;
        this.sqlServer = sqlServer;
    }

    public void setVisible(boolean visible){
        transactionView.setVisible(visible);
    }

    private void setListener() {
        transactionView.getBtnExecute().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == transactionView.getBtnExecute()){

            return;
        }
    }
}
