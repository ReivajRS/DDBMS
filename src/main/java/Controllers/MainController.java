package Controllers;

import Models.MongoDB;
import Models.Neo4j;
import Models.SQLServer;
import Views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private View view;
    private MongoDB mongoDB;
    private Neo4j neo4j;
    private SQLServer sqlServer;

    public MainController(View view, MongoDB mongoDB, Neo4j neo4j, SQLServer sqlServer) {
        this.view = view;
        this.mongoDB = mongoDB;
        this.neo4j = neo4j;
        this.sqlServer = sqlServer;

        setListeners();
    }

    private void setListeners() {
        view.getBtnConfiguration().addActionListener(this);
        view.getBtnTransactions().addActionListener(this);
        view.getBtnQueries().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnConfiguration()) {
            System.out.println("Configuration");
            return;
        }
        if (e.getSource() == view.getBtnTransactions()) {
            System.out.println("Transactions");
            return;
        }
        if (e.getSource() == view.getBtnQueries()) {
            System.out.println("Queries");
            return;
        }
    }
}
